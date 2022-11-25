package dapp.mvp.muckleroutine.service.impl;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import dapp.mvp.muckleroutine.dto.ResultDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.KlipRequestStatus;
import dapp.mvp.muckleroutine.entity.KlipRequestType;
import dapp.mvp.muckleroutine.repository.AppUserRepository;
import dapp.mvp.muckleroutine.repository.KlipRequestRepository;
import dapp.mvp.muckleroutine.repository.RoutineRepository;
import dapp.mvp.muckleroutine.service.KlipRequestService;
import dapp.mvp.muckleroutine.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class KlipRequestServiceImpl implements KlipRequestService {
    private final KlipRequestRepository klipRequestRepository;
    private final AppUserRepository appUserRepository;

    public KlipRequest getSuccessRequest(String request){
        Optional<KlipRequest> optionalKlipRequest = klipRequestRepository.findTopByRequestOrderByRequestExpiredTimeDesc(request);
        if(optionalKlipRequest.isPresent()){
            KlipRequest klipRequest = optionalKlipRequest.get();
            long now = System.currentTimeMillis() / 1000;
            if(now>=klipRequest.getRequestExpiredTime() && klipRequest.getStatus().equals(KlipRequestStatus.COMPLETED))
                return klipRequest;
        }
        return null;

    }

    public KlipRequest save(KlipRequest klipRequest){
        return klipRequestRepository.save(klipRequest);
    }

    public void delete(KlipRequest klipRequest){
        klipRequestRepository.delete(klipRequest);
    }

    public KlipRequest get(Long requestNo){
        Optional<KlipRequest> optionalKlipRequest = klipRequestRepository.findById(requestNo);
        if(optionalKlipRequest.isPresent()){
            return optionalKlipRequest.get();
        }
        return null;
    }

    @Async
    public void checkRequest(KlipRequestType requestType, KlipRequest klipRequest) throws InterruptedException {
        int count = 0;
        while(count < 40) {
            Thread.sleep(3000);
            if (requestType.equals(KlipRequestType.AUTH)) {
                log.info("requestType.equals(KlipRequestType.AUTH");
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request requestAuth = new Request.Builder()
                            .url("https://a2a-api.klipwallet.com/v2/a2a/result?request_key=" + klipRequest.getRequest())
                            .addHeader("content-type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .build();

                    Response resultResponse = client.newCall(requestAuth).execute();
                    com.squareup.okhttp.ResponseBody authBody = resultResponse.body();
                    if (resultResponse.isSuccessful()) {
                        if (authBody != null) {
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObj = (JSONObject) jsonParser.parse(authBody.string());

                            if (jsonObj.getAsString("status").equals("completed")) {
                                Long expirationTime = Long.valueOf((Integer) jsonObj.getAsNumber("expiration_time"));
                                JSONObject result = (JSONObject) jsonParser.parse(jsonObj.getAsString("result"));
                                String address = result.getAsString("klaytn_address");
                                klipRequest.setStatus(KlipRequestStatus.COMPLETED);
                                klipRequest.setRequestExpiredTime(expirationTime);
                                klipRequest = klipRequestRepository.save(klipRequest);

                                System.out.println(address);
                                AppUser appUser = AppUser.builder()
                                        .request(klipRequest)
                                        .walletAddress(address)
                                        .build();
                                appUser = appUserRepository.save(appUser);
                                count = 30;
                                break;
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }
            count++;
        }
    }
}

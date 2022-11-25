package dapp.mvp.muckleroutine.controller;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import dapp.mvp.muckleroutine.dto.ResultDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.KlipRequestStatus;
import dapp.mvp.muckleroutine.entity.KlipRequestType;
import dapp.mvp.muckleroutine.service.KlipRequestService;
import dapp.mvp.muckleroutine.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.hibernate.cfg.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Api(tags = "klip", description = "klip 요청 API")
@RestController
@Log4j2
@RequestMapping("/klip/")
@RequiredArgsConstructor
public class KlipController {
    private final KlipRequestService klipRequestService;
    private final UserService userService;

    @ApiOperation(value = "klip token 생성", notes = "klip login에 필요한 reqest no를 생성합니다.")
    @GetMapping(value = "/auth")
    public ResponseEntity<Object> request(HttpServletRequest request) throws Exception {
        KlipRequest klipRequest = KlipRequest.builder()
            .status(KlipRequestStatus.PREPARED)
            .type(KlipRequestType.AUTH)
            .build();

        klipRequest = klipRequestService.save(klipRequest);
        // request 생성.
        //TODO 리팩토링
        OkHttpClient client = new OkHttpClient();

//        String domain = String.valueOf(request.getRequestURL()).replaceAll(String.valueOf(request.getRequestURI()), "");
        String domain = "http://tostit.i234.me:5003"; //TODO 테스트를 위해서 사용
        MediaType mediaType = MediaType.parse("application/json");
        com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(mediaType, "{\"bapp\": { \"name\" : \"Muckle Routine\" }, \"callback\": { \"success\": \"" + domain + "/klip/callback?type=" + KlipRequestType.AUTH + "&status=" + KlipRequestStatus.COMPLETED + "&requestNo=" + klipRequest.getNo() + "\", \"fail\": \"" + domain + "/klip/callback?type=" + KlipRequestType.AUTH + "&status=" + KlipRequestStatus.COMPLETED + "&requestNo=" + klipRequest.getNo() + "\" }, \"type\": \"auth\"}");

        Request requestAuth = new Request.Builder()
          .url("https://a2a-api.klipwallet.com/v2/a2a/prepare")
          .post(requestBody)
          .addHeader("content-type", "application/json")
          .addHeader("cache-control", "no-cache")
          .build();

        Response response = client.newCall(requestAuth).execute();
        ResultDTO resultDTO = new ResultDTO();
        com.squareup.okhttp.ResponseBody authBody = response.body();
        if (response.isSuccessful()) {
            if (authBody != null) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParser.parse(authBody.string());

                if(jsonObj.getAsString("status").equals("prepared")){
                    String requestNo = jsonObj.getAsString("request_key");
                    Long expirationTime = Long.valueOf((Integer) jsonObj.getAsNumber("expiration_time"));
                    klipRequest.setRequest(requestNo);
                    klipRequest.setRequestExpiredTime(expirationTime);
                    klipRequest = klipRequestService.save(klipRequest);


                    try {
                        klipRequestService.checkRequest(KlipRequestType.AUTH, klipRequest);
                    }catch (Exception ignored){}

                    resultDTO.setStatus("success");
                    resultDTO.setMessage("success");
                    resultDTO.setResult(jsonObj);
                    return new ResponseEntity<>(resultDTO, HttpStatus.OK);
                }
            }
        }

        if(klipRequest.getRequest() == null || klipRequest.getRequestExpiredTime() == null){
            klipRequestService.delete(klipRequest);
        }
        resultDTO.setStatus("fail");
        resultDTO.setMessage("재시도 하거나 관리자에게 문의해주세요.");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/callback")
       public void callback(HttpServletRequest request, HttpServletResponse response, @RequestParam("type") KlipRequestType requestType, @RequestParam("status") KlipRequestStatus status, @RequestParam("requestNo") Long requestNo) throws Exception{
        KlipRequest klipRequest = klipRequestService.get(requestNo);
        String script ="alert('재시도 하거나 관리자에게 문의해주세요.'); window.close();";

        OkHttpClient client = new OkHttpClient();

        Request requestAuth = new Request.Builder()
          .url("https://a2a-api.klipwallet.com/v2/a2a/result?request_key=" + klipRequest.getRequest())
          .addHeader("content-type", "application/json")
          .addHeader("cache-control", "no-cache")
          .build();

        Response resultResponse = client.newCall(requestAuth).execute();
        ResultDTO resultDTO = new ResultDTO();
        com.squareup.okhttp.ResponseBody authBody = resultResponse.body();
        if (resultResponse.isSuccessful()) {
            if (authBody != null) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObj = (JSONObject) jsonParser.parse(authBody.string());

                if(jsonObj.getAsString("status").equals("completed")){
                    String address = jsonObj.getAsString("klaytn_address");
                    Long expirationTime = Long.valueOf((Integer) jsonObj.getAsNumber("expiration_time"));

                    klipRequest.setStatus(KlipRequestStatus.COMPLETED);
                    klipRequest.setRequestExpiredTime(expirationTime);
                    klipRequest = klipRequestService.save(klipRequest);

                    AppUser appUser = AppUser.builder()
                            .request(klipRequest)
                            .walletAddress(address)
                            .build();
                    userService.save(appUser);

                    resultDTO.setStatus("success");
                    resultDTO.setMessage("success");
                    resultDTO.setResult(jsonObj);
                    script = "window.close();";
                }
            }
        }

        response.setContentType("text/html; charset=euc-kr");
        PrintWriter out = response.getWriter();
        out.println("<script>" + script +"</script>");
        out.flush();
    }
}

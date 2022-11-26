package dapp.mvp.muckleroutine.controller;

import com.klaytn.caver.Caver;
import dapp.mvp.muckleroutine.dto.ResultDTO;
import dapp.mvp.muckleroutine.dto.UserDTO;
import dapp.mvp.muckleroutine.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import okhttp3.Credentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.protocol.http.HttpService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Api(tags = "user", description = "사용자(조회) API")
@RestController
@Log4j2
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "사용자 조회", notes = "참가중인 루틴, 종료된 루틴 등 정보를 조회합니다.")
    @GetMapping(value = "/user/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(HttpServletRequest request) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        UserDTO uDTO = userService.entityToDTO(userService.get(request));

        resultDTO.setStatus("success");
        resultDTO.setMessage("success");
        resultDTO.setResult(uDTO);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

//    @Value("${caver.access-key-id}")
//    private String accessKeyId;
//    @Value("${caver.secret-access-key}")
//    private String secretAccessKey;
//    @Value("${caver.chain-id}")
//    private String chainId;

    @Value("${file.dir}")
    private String fileDir;

    @ApiOperation(value = "이미지 업로드", notes = "이미지를 ipfs에 업로드합니다.")
    @PostMapping(value = "/uploadImage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> uploadImage( @RequestParam("image") MultipartFile file) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
//        try{
//            String ipfs_server = "ipfs.infura.io";
//            HttpService httpService = new HttpService("https://node-api.klaytnapi.com/v1/klaytn");
//            httpService.addHeader("Authorization", Credentials.basic(accessKeyId, secretAccessKey));
//            httpService.addHeader("x-chain-id", chainId);
//            Caver caver = new Caver(httpService);
//
//            // Set connection with IPFS Node
//            caver.ipfs.setIPFSNode(ipfs_server, 5001, true);
//            String cid = caver.ipfs.add(file.getBytes());
//            String file_url = "https://" +ipfs_server + "/ipfs/" + cid;
//
//            resultDTO.setStatus("success");
//            resultDTO.setResult(file_url);
//        }catch (Exception e){
//            e.printStackTrace();
//            resultDTO.setStatus("fail");
//
//        }
        String origName = file.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;
        log.info("---------" + savedPath);
        file.transferTo(new File(savedPath));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", savedPath);

        resultDTO.setResult(jsonObject);
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

}


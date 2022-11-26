package dapp.mvp.muckleroutine.controller;

import dapp.mvp.muckleroutine.dto.CertificationDTO;
import dapp.mvp.muckleroutine.dto.ResultDTO;
import dapp.mvp.muckleroutine.dto.RoutineDTO;
import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.CertificationStatus;
import dapp.mvp.muckleroutine.entity.Routine;
import dapp.mvp.muckleroutine.service.CertificationService;
import dapp.mvp.muckleroutine.service.RoutineService;
import dapp.mvp.muckleroutine.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "certification", description = "인증(조회, 업로드, 투표) API")
@RestController
@Log4j2
@RequestMapping("/certification/")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;
    private final UserService userService;

    @ApiOperation(value = "인증 업로드", notes = "인증을 업로드 할 수 있습니다.")
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> upload(@RequestBody CertificationDTO certificationDTO, HttpServletRequest request) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        AppUser appUser = userService.get(request);
        certificationDTO.setUploader(appUser);
        resultDTO.setResult(certificationService.save(certificationDTO));

        resultDTO.setStatus("success");
        resultDTO.setMessage("인증이 업로드되었습니다.");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "인증 조회", notes = "루틴에 대한 가장 마지막 인증의 정보를 가져옵니다.")
    @GetMapping(value = "/get/{routineNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getByRoutine(@PathVariable("routineNo") Long routineNo) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResult(certificationService.getOne(routineNo));

        resultDTO.setStatus("success");
        resultDTO.setMessage("success");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "투표할 인증 조회", notes = "투표할 인증 1개를 조회합니다.")
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get() throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResult(certificationService.getForVote());

        resultDTO.setStatus("success");
        resultDTO.setMessage("success");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "인증 투표", notes = "인증에 투표합니다.")
    @PostMapping(value = "/vote/{certificationNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> vote(HttpServletRequest request, @PathVariable("certificationNo") Long certificationNo, @RequestParam("status") CertificationStatus status, @RequestParam(value = "message", required = false) String message) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        AppUser appUser = userService.get(request);
        certificationService.vote(appUser, certificationNo, status, message);

        resultDTO.setStatus("success");
        resultDTO.setMessage("투표되었습니다.");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}

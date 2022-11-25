package dapp.mvp.muckleroutine.controller;

import dapp.mvp.muckleroutine.dto.ResultDTO;
import dapp.mvp.muckleroutine.dto.RoutineDTO;
import dapp.mvp.muckleroutine.entity.Routine;
import dapp.mvp.muckleroutine.service.RoutineService;
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

@Api(tags = "routine", description = "루틴(추가, 목록조회, 상세조회) API")
@RestController
@Log4j2
@RequestMapping("/routine/")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;

    @ApiOperation(value = "루틴 추가", notes = "루틴을 추가할 수 있습니다.")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody RoutineDTO routineDTO) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResult(routineService.save(routineDTO));

        resultDTO.setStatus("success");
        resultDTO.setMessage("success");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "루틴 목록조회", notes = "루틴을 목록을 조회할 수 있습니다.")
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(@RequestParam(value = "category", defaultValue = "ALL") String category) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        List<Routine> routineList = routineService.getList(category);
        resultDTO.setResult(routineList);

        resultDTO.setStatus("success");
        resultDTO.setMessage("success");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "루틴 상세조회", notes = "루틴의 상세 내용을 조회할 수 있습니다.")
    @GetMapping(value = "/get/{routineNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getDetail(@PathVariable("routineNo") Long routineNo) throws Exception{
        ResultDTO resultDTO = new ResultDTO();
        Routine routine = routineService.getOne(routineNo);
        resultDTO.setResult(routine);

        resultDTO.setStatus("success");
        resultDTO.setMessage("success");
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }


}

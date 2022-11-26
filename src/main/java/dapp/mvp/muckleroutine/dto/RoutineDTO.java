package dapp.mvp.muckleroutine.dto;

import dapp.mvp.muckleroutine.entity.AppUser;
import dapp.mvp.muckleroutine.entity.RoutineTerm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "루틴 DTO", description = "루틴 정보에 해당하는 domain 클래스")
public class RoutineDTO {

    @ApiModelProperty(value = "루틴 번호")
    private Long no;

    @ApiModelProperty(value = "생성자")
    private AppUser creator;

    @ApiModelProperty(value = "제목", required = true)
    private String title;
    @ApiModelProperty(value = "인증 기준 설명", required = true)
    private String description;

    @ApiModelProperty(value = "인증 시작일", required = true)
    private LocalDate startDate;
    @ApiModelProperty(value = "인증 종료일", required = true)
    private LocalDate endDate;

    @ApiModelProperty(value = "첨부 이미지1")
    private String image1;
    @ApiModelProperty(value = "첨부 이미지1")
    private String image2;

    @ApiModelProperty(value = "인증 주기" , required = true, example = "EVERY_DAY, WEEKDAY, WEEKEND, ONCE, TWICE, THREE, FOUR, FIVE, SIX")
    private RoutineTerm term;

    @ApiModelProperty(value = "참가비", required = true)
    private Float fee;

    @ApiModelProperty(value = "최소 인원수", required = true)
    private Long skeleton;

    @ApiModelProperty(value = "참가중인 인원 수", required = true)
    private Long participates;

    @ApiModelProperty(value = "카테고리명", required = true)
    private String category;
}

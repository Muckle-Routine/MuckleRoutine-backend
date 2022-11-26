package dapp.mvp.muckleroutine.dto;

import dapp.mvp.muckleroutine.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "인증 DTO", description = "인증 정보에 해당하는 domain 클래스")
public class CertificationDTO {

    @ApiModelProperty(value = "인증 번호")
    private Long no;

    @ApiModelProperty(value = "생성자")
    private AppUser uploader;

    @ApiModelProperty(value = "인증 내용")
    private String certification;
    private String image;

    @ApiModelProperty(value = "인증 상태", example = "SUCCESS, PROCESS, FAIL")
    private CertificationStatus status;

    @ApiModelProperty(value = "인증 내용", required = true)
    private LocalDateTime certificationDate;

    @ApiModelProperty(value = "총 투표수")
    private Long voteCount;
    @ApiModelProperty(value = "실패 투표수")
    private Long failCount;
    @ApiModelProperty(value = "성공 투표수")
    private Long successCount;

    @ApiModelProperty(value = "실패 사유 목록")
    private List<Board> messages;

    @ApiModelProperty(value = "루틴", required = true)
    private Routine routine;
}

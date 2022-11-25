package dapp.mvp.muckleroutine.dto;

import dapp.mvp.muckleroutine.entity.KlipRequestStatus;
import dapp.mvp.muckleroutine.entity.KlipRequestType;
import dapp.mvp.muckleroutine.entity.Routine;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KlipRequestDTO {

	@ApiModelProperty(value = "request 번호 (id)", required = true)
	private Long no;

	@ApiModelProperty(value = "klip 요청 종류", required = true)
	private KlipRequestType type;

	@ApiModelProperty(value = "klip 요청 상태", required = true)
	private KlipRequestStatus status;

	@ApiModelProperty(value = "Klip 요청 번호")
	private String request;

	@ApiModelProperty(value = "Klip 요청 만료시간 (unix time)")
	private Long requestExpiredTime;
}

package dapp.mvp.muckleroutine.dto;

import dapp.mvp.muckleroutine.entity.KlipRequest;
import dapp.mvp.muckleroutine.entity.Routine;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	@ApiModelProperty(value = "지갑주소", required = true)
	private String walletAddress;

	@ApiModelProperty(value = "Klip 요청 정보")
	private KlipRequest request;

	@ApiModelProperty(value = "참가중인 루틴 목록")
	private List<Routine> participatedRoutine;

}

package dapp.mvp.muckleroutine.dto;


import dapp.mvp.muckleroutine.util.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

	private String status;
	
	private String message;
	
	private Object result;
	
	public void setResult(JSONObject input){
		this.result = input;
	}
	
	public void setResult(Object input) throws Exception {
		this.result = JSONUtil.ObjectToJson(input);
	}
	
	public void setResult(List<?> inputList) throws Exception {
		this.result = JSONUtil.ListToJson(inputList);
	}
}

package dapp.mvp.muckleroutine.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;


import java.util.List;

public class JSONUtil {
	public static JSONObject ObjectToJson(Object input) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // 추가
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 추가
	    
		String jsonInString = mapper.writeValueAsString(input);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(jsonInString);
		JSONObject jsonObj = (JSONObject) obj;
		return jsonObj;
	}
	public static JSONArray ListToJson(List<?> list) throws Exception{
		JSONArray jsonArray = new JSONArray();
		for(Object object : list) {
			jsonArray.add(ObjectToJson(object));
		}
		return jsonArray;
	}

}

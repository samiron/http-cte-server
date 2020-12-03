package of.cgi.assignment.services.pojo;

import java.util.HashMap;
import java.util.Map;

public class FormData {

	private final Map<String, String> data = new HashMap<>();

	public FormData(String encodedData){
		String[] fields = encodedData.split("&");
		for(String field : fields){
			String[] pair = field.split("=");
			data.put(pair[0], pair[1]);
		}
	}

	public String getFieldValue(String fieldName){
		return data.get(fieldName);
	}
}

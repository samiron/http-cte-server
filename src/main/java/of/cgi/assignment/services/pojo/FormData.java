package of.cgi.assignment.services.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class FormData {

	private final Map<String, String> data = new HashMap<>();

	public FormData(String encodedData) throws UnsupportedEncodingException {
		String[] fields = encodedData.split("&");
		for(String field : fields){
			String[] pair = field.split("=");
			data.put(URLDecoder.decode(pair[0], "UTF-8"),
					URLDecoder.decode(pair[1], "UTF-8"));
		}
	}

	public String getFieldValue(String fieldName){
		return data.get(fieldName);
	}
}

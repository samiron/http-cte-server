package of.cgi.assignment.http.request;

import of.cgi.assignment.http.HttpMethod;

import java.util.Map;

public class GetRequest extends BasicHttpRequest {
	public GetRequest(){
		super(HttpMethod.GET);
	}
}

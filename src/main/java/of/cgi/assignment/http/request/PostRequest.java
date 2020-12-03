package of.cgi.assignment.http.request;

import of.cgi.assignment.http.HttpMethod;

public class PostRequest extends BasicHttpRequest {

	public PostRequest(){
		super(HttpMethod.POST);
	}
}

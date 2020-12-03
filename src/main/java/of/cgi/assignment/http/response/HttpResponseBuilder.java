package of.cgi.assignment.http.response;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.request.HttpRequest;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HttpResponseBuilder {
	private Serializable body;
	private ContentType contentType;
	private ResponseCode responseCode;
	private List<String> headers;
	private HttpRequest httpRequest;

	public HttpResponseBuilder() {
		headers = new ArrayList<>();
		headers.add("Server: Chunked Encoder");
	}

	public HttpResponseBuilder body(Serializable body) {
		this.body = body;
		return this;
	}

	public HttpResponseBuilder contentType(ContentType contentType) {
		this.contentType = contentType;
		return this;
	}

	public HttpResponseBuilder request(HttpRequest request) {
		this.httpRequest = request;
		return this;
	}

	public HttpResponseBuilder responseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
		return this;
	}

	public HttpResponseBuilder addHeader(String headerLine) {
		headers.add(headerLine);
		return this;
	}

	public HttpResponse build() {
		if(this.body instanceof File){
			return new FileStreamResponse(this.body, this.contentType, this.responseCode, this.headers);
		}
		return new BasicHttpResponse(this.body, this.contentType, this.responseCode, this.headers);
	}


}

package of.cgi.assignment.http;

public enum ResponseCode {
	//2xx
	OK("200 OK"),

	//4xx
	CLIENT_ERROR("400 Bad Request"),
	NOT_FOUND("401 Not Found"),
	METHOD_NOT_ALLOWED ("405 Method Not Allowed"),
	UNSUPPORTED_MEDIA_TYPE("415 Unsupported Media Type"),

	//5xx,
	INTERNAL_SERVER_ERROR("500 Internal Server Error");

	private final String response;

	ResponseCode(String s) {
		this.response = s;
	}

	@Override
	public String toString() {
		return response;
	}
}

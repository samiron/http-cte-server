package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;

public class HttpException extends Throwable {
	private final ResponseCode responseCode;
	private final String exceptionBody;
	private final ContentType contentType = ContentType.TXT;

	public HttpException(ResponseCode responseCode, String msg) {
		super(msg);
		this.responseCode = responseCode;
		this.exceptionBody = msg;
	}

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public String getExceptionBody() {
		return exceptionBody;
	}

	public ContentType getContentType() {
		return contentType;
	}
}

package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ResponseCode;

public class UnsupportedMediaType extends HttpException {
	public UnsupportedMediaType(String msg) {
		super(ResponseCode.UNSUPPORTED_MEDIA_TYPE, msg);
	}
}

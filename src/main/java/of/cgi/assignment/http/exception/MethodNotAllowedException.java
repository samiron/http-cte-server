package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ResponseCode;

public class MethodNotAllowedException extends HttpException {
	public MethodNotAllowedException(String message) {
		super(ResponseCode.METHOD_NOT_ALLOWED, message);
	}
}

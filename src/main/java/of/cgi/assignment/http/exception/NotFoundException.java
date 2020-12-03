package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ResponseCode;

public class NotFoundException extends HttpException {
	public NotFoundException(String msg) {
		super(ResponseCode.NOT_FOUND, msg);
	}
}

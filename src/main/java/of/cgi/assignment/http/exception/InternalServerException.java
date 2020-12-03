package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ResponseCode;

public class InternalServerException extends HttpException {
	public InternalServerException(String msg) {
		super(ResponseCode.INTERNAL_SERVER_ERROR, msg);
	}
}

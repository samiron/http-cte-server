package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ResponseCode;

public class NotImplementedException extends HttpException {
	public NotImplementedException(String msg) {
		super(ResponseCode.NOT_IMPLEMENTED, msg);
	}
}

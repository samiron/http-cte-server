package of.cgi.assignment.http.exception;

import of.cgi.assignment.http.ResponseCode;

public class BadRequestException extends HttpException {

	public BadRequestException(String message) {
		super(ResponseCode.CLIENT_ERROR, message);
	}
}

package of.cgi.assignment.handlers;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.http.response.HttpResponseBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class ErrorHandler {

	private final ResponseCode responseCode;
	private final ContentType contentType;
	private final Socket socket;
	private final Serializable body;

	public ErrorHandler(Socket socket, HttpException e) {
		this.socket = socket;
		this.responseCode = e.getResponseCode();
		this.contentType = e.getContentType();
		this.body = e.getExceptionBody();
	}

	public ErrorHandler(Socket socket, Exception e) {
		this.socket = socket;
		this.responseCode = ResponseCode.INTERNAL_SERVER_ERROR;
		this.contentType = ContentType.TXT;
		this.body = e.getMessage();
	}

	public void handle() {
		HttpResponse response = new HttpResponseBuilder()
				.contentType(ContentType.TXT)
				.responseCode(responseCode)
				.body(body)
				.build();
		try {
			response.flush(socket.getOutputStream());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

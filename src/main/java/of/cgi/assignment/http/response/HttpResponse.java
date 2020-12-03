package of.cgi.assignment.http.response;

import of.cgi.assignment.http.exception.HttpException;

import java.io.OutputStream;

public interface HttpResponse {
	void flush(OutputStream outputStream) throws HttpException;
}

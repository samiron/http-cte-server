package of.cgi.assignment.http.response;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.InternalServerException;
import of.cgi.assignment.http.exception.NotFoundException;

import java.io.*;
import java.util.List;

public class FileStreamResponse extends BasicHttpResponse {

	FileStreamResponse(Serializable body, String contentType, ResponseCode responseCode, List<String> headers) {
		super(body, contentType, responseCode, headers);
	}

	@Override
	protected InputStream prepareBody(PrintStream out) throws HttpException {
		if(!(this.getBody() instanceof File)){
			throw new InternalServerException("Response is not a file");
		}

		File f = (File) this.getBody();

		try {
			return new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new NotFoundException("Missing File");
		}
	}
}

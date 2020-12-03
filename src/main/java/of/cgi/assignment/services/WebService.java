package of.cgi.assignment.services;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.BadRequestException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.http.response.HttpResponseBuilder;
import of.cgi.assignment.services.pojo.FormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WebService extends HttpService {

	Logger logger = LoggerFactory.getLogger(WebService.class);
	private static final String WEBROOT = "./webroot";

	@Override
	public HttpResponse doGet(HttpRequest request) {

		logger.info("Requested for: " + request.getPath());
		String path = request.getPath().replaceFirst("web", "");
		File f = new File(WEBROOT + path);

		String mimeType = null;
		try {
			mimeType = Files.probeContentType(Paths.get(f.getPath()));
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}

		return new HttpResponseBuilder()
				.responseCode(ResponseCode.OK)
				.contentType(mimeType)
				.body(f)
				.addHeader("X-Samiron: Random Header")
				.build();

	}

	@Override
	public HttpResponse doPost(HttpRequest request) throws BadRequestException {
		FormData formData = getFormData(request);
		String firstName = formData.getFieldValue("first_name");
		String lastName = formData.getFieldValue("last_name");

		String response = "<!DOCTYPE html>\n" +
				"<html>\n" +
				"<head>\n" +
				"    <title>Pauls Web Server</title>\n" +
				"</head>" +
				"<body>" +
				"<h1>\n" +
				"    Hi " + firstName + " " + lastName +
				"</h1>" +
				"</body>" +
				"</html>";

		return new HttpResponseBuilder()
				.responseCode(ResponseCode.OK)
				.contentType(ContentType.HTML)
				.body(response)
				.build();
	}

	private FormData getFormData(HttpRequest request) throws BadRequestException {
		FormData formData;
		try {
			formData = new FormData(request.getRequestBody());
		} catch (UnsupportedEncodingException e) {
			throw new BadRequestException("Failed parse data");
		}
		return formData;
	}
}

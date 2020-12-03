package of.cgi.assignment.services;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.http.response.HttpResponseBuilder;
import of.cgi.assignment.services.pojo.FormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebService extends HttpService {

	Logger logger = LoggerFactory.getLogger(WebService.class);
	private static final String WEBROOT = "./webroot";

	@Override
	public HttpResponse doGet(HttpRequest request) {

		logger.info("Requested for: " + request.getPath());
		String path = request.getPath().replaceFirst("web", "");
		File f = new File(WEBROOT + path);

		return new HttpResponseBuilder()
				.responseCode(ResponseCode.OK)
				.contentType(ContentType.HTML)
				.body(f)
				.addHeader("X-Samiron: Random Header")
				.build();

	}

	@Override
	public HttpResponse doPost(HttpRequest request) {
		FormData formData = new FormData(request.getRequestBody());
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
}

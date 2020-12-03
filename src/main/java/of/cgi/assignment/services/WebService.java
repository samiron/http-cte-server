package of.cgi.assignment.services;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.NotFoundException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.http.response.HttpResponseBuilder;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class WebService extends HttpService {

	Logger logger = LoggerFactory.getLogger(WebService.class);
	private static final String WEBROOT = "./webroot";

	@Override
	public HttpResponse doGet(HttpRequest request) throws HttpException {

		logger.info("Requested for: " + request.getPath());
		File f = new File( WEBROOT + request.getPath());

		return new HttpResponseBuilder()
				.responseCode(ResponseCode.OK)
				.contentType(ContentType.HTML)
				.body(f)
				.addHeader("X-Samiron: Random Header")
				.build();

	}

	@Override
	public HttpResponse doPost(HttpRequest request) {
		return null;
	}
}

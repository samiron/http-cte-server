package of.cgi.assignment.http.request;

import of.cgi.assignment.http.exception.BadRequestException;
import of.cgi.assignment.http.exception.HttpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpRequestBuilder implements RequestBuilder {

	private BasicHttpRequest httpRequest;
	Map<String, String> headers;

	public HttpRequestBuilder(InputStream inputStream) throws HttpException, IOException {
		parse(inputStream);
	}

	private void parse(InputStream inputStream) throws IOException, HttpException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		requestLine(reader.readLine());

		String nextLine = reader.readLine();
		while (!nextLine.equals("")) {
			headerLine(nextLine);
			nextLine = reader.readLine();
		}

		StringBuilder bodyBuilder = new StringBuilder();
		if (reader.ready()) {
			int c  = reader.read();
			while (c != -1) {
				bodyBuilder.append((char) c);
				if(!reader.ready()) break;
				c  = reader.read();
			}
		}
		this.httpRequest.setRequestBody(bodyBuilder.toString());
	}

	private void headerLine(String headerLine) throws HttpException {
		String[] parts = headerLine.split(":", 2);
		if (parts.length != 2) {
			throw new BadRequestException("Malformed Header: " + headerLine);
		}
		httpRequest.addHeader(parts[0].trim(), parts[1].trim());
	}

	private void requestLine(String requestLine) throws HttpException {
		String[] tokens = requestLine.split("\\s+");
		if (tokens.length != 3) {
			throw new BadRequestException("Malformed Start Line: " + requestLine);
		}
		String method = tokens[0];
		String url = tokens[1];
		String httpVersion = tokens[2];
		httpRequest = createRequest(method.trim());
		httpRequest.setHttpVersion(httpVersion.trim());
		try {
			httpRequest.setUrl(url.trim());
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
			throw new BadRequestException("Malformed Url");
		}
	}

	public HttpRequest build() {
		return httpRequest;
	}

	private BasicHttpRequest createRequest(String method) throws HttpException {
		switch (method.toUpperCase()) {
			case "GET":
				return new GetRequest();
			case "POST":
				return new PostRequest();
			default:
				throw new BadRequestException("Invalid request method: " + method);
		}
	}
}

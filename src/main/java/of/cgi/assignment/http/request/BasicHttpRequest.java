package of.cgi.assignment.http.request;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.HttpMethod;
import of.cgi.assignment.http.exception.BadRequestException;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.UnsupportedMediaType;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class BasicHttpRequest implements HttpRequest {

	private URI url;
	private String body = "";
	private String httpVersion = "";

	private HttpMethod httpMethod;
	private final Map<String, String> queryParams = new HashMap<>();
	private final List<ContentType> accepts = new ArrayList<>();
	private final Map<String, String> headers = new HashMap<>();
	private ContentType contentType;
	private Integer contentLength;

	@Override
	public ContentType getContentType() {
		return this.contentType;
	}

	@Override
	public Integer getContentLength() {
		return this.contentLength;
	}

	@Override
	public HttpMethod getMethod() {
		return this.httpMethod;
	}

	@Override
	public String getPath() {
		return url.getPath();
	}

	@Override
	public Map<String, String> getQueryParams() {
		return this.queryParams;
	}

	@Override
	public String getHttpVersion() {
		return null;
	}

	@Override
	public String getRequestBody() {
		return body;
	}

	@Override
	public String getHeader(String headerName) {
		return null;
	}

	@Override
	public String toString() {
		return "BasicHttpRequest{" +
				"url=" + url +
				", body='" + body + '\'' +
				", httpVersion='" + httpVersion + '\'' +
				", httpMethod=" + httpMethod +
				", queryParams=" + queryParams.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining(", ")) +
				", accepts=" + accepts +
				", headers=" + headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining(", ")) +
				", contentType=" + contentType +
				", contentLength=" + contentLength +
				'}';
	}

	public String getHost() {
		return getHeader("Host");
	}

	public List<ContentType> getAccepts() {
		return accepts;
	}

	public boolean doesAccept(ContentType contentType) {
		return accepts.contains(contentType);
	}

	BasicHttpRequest(HttpMethod method) {
		this.httpMethod = method;
	}

	void setUrl(String uri) throws MalformedURLException, URISyntaxException {
		this.url = new URI(uri);
		String queryPart = url.getQuery();
		if (queryPart != null) {
			String[] queries = queryPart.split("&");
			for (String query : queries) {
				String[] pair = query.split("=");
				queryParams.put(pair[0], pair[1]);
			}
		}
	}

	void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	void setRequestBody(String body) {
		this.body = body;
	}

	public void setHttpMethod(String method) {
		this.httpMethod = HttpMethod.valueOf(method);
	}

	void addHeader(String headerName, String headerValue) throws HttpException {
		headers.put(headerName, headerValue);
		parseSpecialHeaders(headerName, headerValue);
	}

	private void parseSpecialHeaders(String headerName, String headerValue) throws HttpException {
		if (headerName.equalsIgnoreCase("Accept")) {
			parseAcceptHeaders(headerValue);
		} else if (headerName.equalsIgnoreCase("Content-Type")) {
			parseContentType(headerValue);
		} else if (headerName.equalsIgnoreCase("Content-Length")) {
			parseContentLength(headerValue);
		}
	}

	private void parseContentType(String headerValue) throws UnsupportedMediaType {
		List<ContentType> contentTypes = ContentType.lookUp(new String[]{
				headerValue
		});
		if (contentTypes.size() < 1) {
			throw new UnsupportedMediaType("\"" + headerValue + "\" not supported");
		}
		this.contentType = contentTypes.get(0);
	}

	private void parseContentLength(String headerValue) throws BadRequestException {
		try {
			this.contentLength = Integer.parseInt(headerValue);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Invalid content length");
		}
	}

	private void parseAcceptHeaders(String headerValue) {
		String[] allTypes = headerValue.split(",");
		List<ContentType> contentTypes = ContentType.lookUp(allTypes);
		accepts.addAll(contentTypes);
	}
}

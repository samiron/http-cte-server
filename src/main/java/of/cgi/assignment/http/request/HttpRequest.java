package of.cgi.assignment.http.request;

import of.cgi.assignment.http.ContentType;

import java.util.Map;

public interface HttpRequest {

	ContentType getContentType();

	Integer getContentLength();

	String getPath();

	Map<String, String> getQueryParams();

	String getHttpVersion();

	String getRequestBody();

	String getHeader(String headerName);

}

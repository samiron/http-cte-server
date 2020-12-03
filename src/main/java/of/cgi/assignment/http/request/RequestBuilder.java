package of.cgi.assignment.http.request;

import of.cgi.assignment.http.exception.BadRequestException;

public interface RequestBuilder {

	HttpRequest build() throws BadRequestException;

}

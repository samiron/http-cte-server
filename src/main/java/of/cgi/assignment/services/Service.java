package of.cgi.assignment.services;

import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;

public interface Service {

	HttpResponse serve(HttpRequest request) throws HttpException;

}

package of.cgi.assignment.services;

import of.cgi.assignment.http.HttpMethod;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.MethodNotAllowedException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;

public abstract class HttpService implements Service {

	@Override
	public HttpResponse serve(HttpRequest request) throws HttpException {
		if (request.getMethod().equals(HttpMethod.GET)) {
			return doGet(request);
		} else if (request.getMethod().equals(HttpMethod.POST)) {
			return doPost(request);
		} else {
			throw new MethodNotAllowedException(request.getMethod() + " is not allowed");
		}
	}

	abstract public HttpResponse doGet(HttpRequest request) throws HttpException;

	abstract public HttpResponse doPost(HttpRequest request) throws HttpException;

}

package of.cgi.assignment.services;

import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;

public abstract class HttpService implements Service {

	@Override
	public HttpResponse serve(HttpRequest request) throws HttpException {
		return null;
	}

	abstract public HttpResponse doGet(HttpRequest request) throws HttpException;

	abstract public HttpResponse doPost(HttpRequest request) throws HttpException;

}

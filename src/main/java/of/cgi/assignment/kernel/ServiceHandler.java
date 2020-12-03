package of.cgi.assignment.kernel;

import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.services.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceHandler<T extends Service> {
	private final T serviceClass;
	private final Method method;

	ServiceHandler(T serviceClass, Method method) {
		this.serviceClass = serviceClass;
		this.method = method;
	}

	public HttpResponse invoke(HttpRequest request) throws InvocationTargetException, IllegalAccessException {
		return (HttpResponse) this.method.invoke(this.serviceClass, request);
	}
}

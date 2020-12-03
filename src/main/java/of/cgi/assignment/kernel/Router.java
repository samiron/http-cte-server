package of.cgi.assignment.kernel;

import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.services.Service;
import of.cgi.assignment.services.WebService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {
	private static final Router router = new Router();
	Map<String, Service> routes = new HashMap<>();

	private Router(){
		routes.put("webService", new WebService());
	}

	public static Router get() {
		synchronized (router){
			return router;
		}
	}

	public ServiceHandler<Service> getHandler(HttpRequest request) throws NoSuchMethodException {
		synchronized (router){
			Service serviceClass = routes.get("webService");
			Method method = serviceClass.getClass().getDeclaredMethod("doGet", HttpRequest.class);
			return new ServiceHandler<>(serviceClass, method);
		}
	}
}

package of.cgi.assignment.kernel;

import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.NotFoundException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.services.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class Router {
	private static final Router router = new Router();

	private final Map<String, Service> routes = new ConcurrentHashMap<>();

	private Router() {

		Map<String, String> routeConfig = Configuration.get().getRoutes();
		for (Map.Entry<String, String> routeEntry :
				routeConfig.entrySet()) {
			try {
				Class<Service> clazz;
				clazz = (Class<Service>) Class.forName(routeEntry.getValue());
				Service service;
				service = clazz.newInstance();
				this.routes.put(routeEntry.getKey(), service);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to initialize route", e);
			}
		}
	}

	public static Router get() {
		synchronized (router) {
			return router;
		}
	}

	public ServiceHandler<Service> getHandler(HttpRequest request) throws NoSuchMethodException, HttpException {
		String fullPath = request.getPath();
		String[] partParts = fullPath.split("/", 3);
		String prefix = partParts[1];

		Service serviceClass = routes.get(prefix);
		if (serviceClass == null) {
			throw new NotFoundException("Invalid path");
		}

		Method method = serviceClass.getClass().getMethod("serve", HttpRequest.class);
		return new ServiceHandler<>(serviceClass, method);
	}

}

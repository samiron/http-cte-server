
package of.cgi.assignment.kernel;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class Configuration {

	Logger logger = LoggerFactory.getLogger(Configuration.class);
	private final static String APP_CONFIG_FILE;
	private final static String LOG_CONFIG_FILE;

	private final static Map<String, String> routes = new ConcurrentHashMap<>();
	private final static Map<String, String> directories = new ConcurrentHashMap<>();

	static {
		String propertyDirectory = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
		LOG_CONFIG_FILE = propertyDirectory + "log4j.properties";
		APP_CONFIG_FILE = propertyDirectory + "application.properties";
		PropertyConfigurator.configure(LOG_CONFIG_FILE);
	}

	private static final Configuration configuration = new Configuration();
	Properties properties;

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String def) {
		return properties.getProperty(key, def);
	}

	public Map<String, String> getRoutes() {
		return routes;
	}

	public Map<String, String> getDirectories() {
		return directories;
	}

	public static Configuration get() {
		synchronized (configuration) {
			return configuration;
		}
	}

	private Configuration() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(APP_CONFIG_FILE));
			parse();
		} catch (IOException e) {
			logger.error("Failed to load application properties", e);
			e.printStackTrace();
		}
	}

	private void parse() {
		Enumeration<String> e = (Enumeration<String>) properties.propertyNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();

			if (name.startsWith("route")) {
				addRoute(name.split("\\.")[1], (String) properties.get(name));
			}

			if (name.startsWith("directories")) {
				addDirectory(name.split("\\.")[1], (String) properties.get(name));
			}
		}
	}

	private void addDirectory(String name, String path) {
		directories.put(name, path);
	}

	private void addRoute(String name, String fqcn) {
		routes.put(name, fqcn);
	}
}

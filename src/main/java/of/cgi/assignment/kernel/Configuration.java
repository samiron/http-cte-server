package of.cgi.assignment.kernel;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Configuration {

	Logger logger = LoggerFactory.getLogger(Configuration.class);
	private final static String APP_CONFIG_FILE;
	private final static String LOG_CONFIG_FILE;

	static {
		String propertyDirectory = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
		LOG_CONFIG_FILE = propertyDirectory + "log4j.properties";
		APP_CONFIG_FILE = propertyDirectory + "application.properties";
		PropertyConfigurator.configure(LOG_CONFIG_FILE);
	}

	private static final Configuration configuration = new Configuration();
	Properties properties;

	public String getProperty(String key){
		return properties.getProperty(key);
	}
	public String getProperty(String key, String def){
		return properties.getProperty(key, def);
	}

	public static Configuration get(){
		synchronized (configuration){
			return configuration;
		}
	}

	private Configuration(){
		properties = new Properties();
		try {
			properties.load(new FileInputStream(APP_CONFIG_FILE));
		} catch (IOException e) {
			logger.error("Failed to load application properties", e);
			e.printStackTrace();
		}
	}
}

package of.cgi.assignment.kernel;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ConfigurationTest {

	private Configuration configuration;

	@BeforeEach
	public void setup(){
		configuration = Configuration.get();
	}

	@Test
	void getProperty() {
		assertThat(configuration.getProperty("version"), is(equalTo("1.0")));
	}

	@Test
	void getPropertyWithDefault() {
		assertThat(configuration.getProperty("random-key", "random-value"), is(equalTo("random-value")));
	}

	@Test
	void getRoutes() {
		Map<String, String> routes = configuration.getRoutes();
		assertThat(routes.size(), is(equalTo(2)));
		assertThat(routes.get("webService"), is(equalTo("of.cgi.assignment.services.WebService")));
		assertThat(routes.get("directoryService"), is(equalTo("of.cgi.assignment.services.DirectoryService")));
	}

	@Test
	void getDirectories() {
		Map<String, String> routes = configuration.getDirectories();
		assertThat(routes.size(), is(equalTo(2)));
		assertThat(routes.get("images"), is(equalTo("e:\\images")));
		assertThat(routes.get("documents"), is(equalTo("e:\\documents")));
	}
}
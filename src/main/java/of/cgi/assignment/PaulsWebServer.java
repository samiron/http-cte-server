package of.cgi.assignment;

import of.cgi.assignment.handlers.RequestHandler;
import of.cgi.assignment.kernel.Configuration;
import of.cgi.assignment.kernel.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaulsWebServer {

	private static final int PORT = 8080;
	private static final Logger logger = LoggerFactory.getLogger(PaulsWebServer.class);
	private final ExecutorService threadPool;
	private final int nThreads = 5;

	public static void main(String[] args) {
		init();
		try {
			new PaulsWebServer()
					.start();
		} catch (IOException e) {
			logger.error("Failed to server. Please try again.");
			e.printStackTrace();
		}
	}

	private static void init() {
		Configuration.get();
		Router.get();
	}

	public PaulsWebServer() {
		threadPool = Executors.newFixedThreadPool(nThreads);
	}

	private void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		logger.info("Server listening to {}", PORT);
		while(true){
			threadPool.submit(new RequestHandler(serverSocket.accept()));
		}
	}

}

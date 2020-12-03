package of.cgi.assignment.handlers;

import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.request.HttpRequestBuilder;
import of.cgi.assignment.http.request.RequestBuilder;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.kernel.Router;
import of.cgi.assignment.kernel.ServiceHandler;
import of.cgi.assignment.services.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class RequestHandler implements Runnable {
	Logger logger = LoggerFactory.getLogger(RequestHandler.class);
	private final Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			logger.info("Processing request");
			InputStream inputStream = socket.getInputStream();
			RequestBuilder requestBuilder = new HttpRequestBuilder(inputStream);

			HttpRequest request = requestBuilder.build();
			System.out.println(request);

			ServiceHandler<Service> handler = Router.get().getHandler(request);
			HttpResponse response = handler.invoke(request);
			response.flush(socket.getOutputStream());

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorHandler(socket, e).handle();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			if(e.getCause() instanceof HttpException){
				new ErrorHandler(socket, (HttpException) e.getCause()).handle();
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ErrorHandler(socket, e).handle();
		} catch (HttpException e) {
			e.printStackTrace();
			new ErrorHandler(socket, e).handle();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				logger.error("Fatal error. Completely failed to respond.");
				e.printStackTrace();
			}
		}
	}
}

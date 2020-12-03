package of.cgi.assignment.services;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.NotFoundException;
import of.cgi.assignment.http.exception.NotImplementedException;
import of.cgi.assignment.http.request.HttpRequest;
import of.cgi.assignment.http.response.HttpResponse;
import of.cgi.assignment.http.response.HttpResponseBuilder;
import of.cgi.assignment.kernel.Configuration;
import of.cgi.assignment.services.html.Table;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class DirectoryService extends HttpService {

	@Override
	public HttpResponse doGet(HttpRequest request) throws HttpException {
		String requestedPath = request.getPath();
		System.out.println("Requested Path: " + request.getPath());

		File directory = lookupDirectory(requestedPath);
		Table table = new Table("type", "name");
		for (File file : Objects.requireNonNull(directory.listFiles())) {
			if (file.isDirectory()) {
				table.addRow(Arrays.asList("dir", anchor(file.getName(), requestedPath + File.separator + file.getName())));
			} else {
				table.addRow(Arrays.asList("file", file.getName()));
			}
		}

		String responseBuilder = "<html>" +
				header() +
				"<body>" +
				table.toHtml() +
				"</body>";

		return new HttpResponseBuilder()
				.contentType(ContentType.HTML)
				.responseCode(ResponseCode.OK)
				.body(responseBuilder)
				.build();
	}

	private String header() {
		return "<head>" +
				"<link rel=\"stylesheet\" href=\"/web/style/bootstrap.min.css\" />" +
				"</head>";
	}

	private String anchor(String value, String href) {
		return "<a href=\"" +
				href + "\">" +
				value +
				"</a>";
	}

	private File lookupDirectory(String requestedPath) throws HttpException {
		String[] parts = requestedPath.split("/", 4);
		String dirKey = parts[2];
		String dirPath = Configuration.get().getDirectories().get(dirKey);
		if (dirPath == null) {
			throw new NotFoundException("Unknown path");
		}

		if (parts.length >= 4) {
			dirPath = dirPath + File.separator + parts[3];
		}

		File f = new File(dirPath);
		if (!f.isDirectory()) {
			throw new NotFoundException("No directory found");
		}
		return f;
	}

	@Override
	public HttpResponse doPost(HttpRequest request) throws HttpException {
		throw new NotImplementedException("File upload is not supported");
	}
}

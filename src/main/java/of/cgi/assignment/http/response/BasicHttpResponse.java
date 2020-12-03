package of.cgi.assignment.http.response;

import of.cgi.assignment.http.ContentType;
import of.cgi.assignment.http.ResponseCode;
import of.cgi.assignment.http.exception.HttpException;
import of.cgi.assignment.http.exception.InternalServerException;
import of.cgi.assignment.kernel.Configuration;

import java.io.*;
import java.util.List;

public class BasicHttpResponse implements HttpResponse {

	private final Serializable body;

	private final ContentType contentType;
	private final ResponseCode responseCode;
	private final List<String> headers;
	private final String lineSeparator = "\r\n";
	private final int chunkSize;

	BasicHttpResponse(Serializable body, ContentType contentType, ResponseCode responseCode, List<String> headers) {
		this.body = body;
		this.contentType = contentType;
		this.responseCode = responseCode;
		this.headers = headers;
		this.chunkSize = Integer.parseInt(Configuration.get().getProperty("chunkSize", "1024"));
	}

	protected Serializable getBody() {
		return body;
	}

	@Override
	public void flush(OutputStream outputStream) throws HttpException {
		PrintStream out = new PrintStream(new BufferedOutputStream(outputStream));
		InputStream in = prepareBody(out);

		// Flush the headers first
		fillInHeaders(out);
		flush(out);
		sendBody(in, out);
	}

	protected InputStream prepareBody(PrintStream out) throws HttpException {
		if(!(this.body instanceof String)){
			throw new InternalServerException("Response is not String");
		}
		return new ByteArrayInputStream(((String) this.body).getBytes());
	}

	protected void sendBody(InputStream in, PrintStream printOut) throws InternalServerException {
		int position = 0;
		int nBytes;

		byte[] bytes = new byte[chunkSize];
		try {
			nBytes = in.read(bytes, position, chunkSize);
			while(nBytes != -1){
				sendChunk(printOut, position, nBytes, bytes);
				nBytes = in.read(bytes, position, chunkSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new InternalServerException("Failed to parse body");
		}

		printOut.print("0" + lineSeparator);
		flush(printOut);
	}

	private void sendChunk(PrintStream printOut, int position, int nBytes, byte[] bytes) {
		printOut.print(Integer.toHexString(nBytes));
		printOut.print(lineSeparator);
		printOut.write(bytes, position, nBytes);
		flush(printOut);
	}

	protected void fillInHeaders(PrintStream out) {
		out.print("HTTP/1.1 " + responseCode + lineSeparator);
		out.print("Content-Type: " + contentType + lineSeparator);
		out.print("Transfer-Encoding: chunked" + lineSeparator);
	}

	protected void flush(PrintStream out){
		out.print(lineSeparator);
		out.flush();
	}
}

# Http Chunked Encoding Server

## How to use
### Configuration
All the application configurations should be done in `application.properties` file

* `chunkSize`: determines the maximum chunk size.
* `route.xyz`: Is used to register a service handler class. Application by default provide two services
  * `DirectoryService`: Used to expose a directory to be accessible from web browser.
  * `WebService`: Used to serve files from `webroot`.
  * `xyz`: must be the prefix to locate the corresponding service.
  * Example, 
    * http://localhost:8080/web/index.html will access index.html from webroot. 
    * http://localhost:8080/dir/images will display the contents of the directory as configured in properties file.

* `directories.xyz`: Used to configure exposed directories.

### Run the application
Using following maven command to run the application, Press Ctrl+C to shut down.

`mvn exec:java -Dexec.mainClass="of.cgi.assignment.PaulsWebServer"`

### Run test
Run `mvn test` to run the tests.

## Application Design

**Highlights:**

The Chunked Transfer Encoding is implemented in `of.cgi.assignment.http.response.BasicHttpResponse.sendBody` function.

**Class: `PaulsWebServer`**

Contains the main method. It initializes `Configuration` and `Router`. Opens the socket to listen for requests.
Once a request received, it creates a RequestHandler that runs in one of the threads in the threadpool.

**package: `*.kernel.*`**
* `Configuration`: Use java Properties to read the application properties file. Also initializes the logging system.
* `Router`: Use the `route.*` part of the configuration.
* `ServiceHandler`: Router returns a `ServiceHandler`. Basically points to the service class.

**Package: `*.handlers.*`**

This package contains two handlers, `RequestHandler` and `ErrorHandler`.

* `RequestHandler`: Build `HttpRequest` object, invokes routers to get the `ServiceHandler`, invoke the ServiceHandler to get the response.
* `ErrorHandler`: If any error occurs in handling the request, the `RequestHandler` delegates the responsibility to `ErrorHandler`.
It can process either a `HttpException` or any java `Exception`.

**Package: `*.services.*`**
* `DirectoryService`: This service is responsible to expose a configured directory.
  * Configure a `directories.XYZ=/a/valid/path`, Then access that from http://localhost:8080/dir/XYZ
* `WebService`: This service serves file from webroot. Put any file in webroot and then access it from web browser. 
For quick experiment, you can head on http://localhost:8080/web/index.html. You will see a form, enter your name and submit.
  You will be greeted well.

**Package: `*.http.*`**
* `.request.*` contains http request related classes.
* `.response.*` contains http response related classes.
* `.exception.*` contains some Http Exception classes that are used here.

package ru.serverflot;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class Param {
    String Name;
    String Value;

    public Param(String Name, String Value) {
        this.Name = Name;
        this.Value = Value;
    }
}

public class MyHttpHandler implements HttpHandler {
    private Logger logger;

    private void initLogger() {
        logger = Logger.getLogger(this.getClass().getName());
        FileHandler fh;

        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(this.getClass().getName() + ".log");
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            // the following statement is used to log any messages
            logger.info("Logging started");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Param param = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
              param = handleGetParam(httpExchange);
        } else if ("POST".equals(httpExchange.toString())) {
//            requestParamValue = handlePostRequest(httpExchange);
        }

        handleResponse(httpExchange, param);
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        return httpExchange.toString();
    }

    private Param handleGetParam(HttpExchange httpExchange) {
        String param = httpExchange.getRequestURI().toString().split("\\?")[1];
        return new Param(param.substring(0, param.indexOf("=")), param.split("=")[1]);
    }

    private void handleResponse(HttpExchange httpExchange, Param param) throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder
                .append("<h1>")
                .append(param.Name + ": " + param.Value)
                .append("</h1>");

        // encode HTML content
        String htmlResponse = htmlBuilder.toString();

        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());

        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    MyHttpHandler() {
        initLogger();
    }
}

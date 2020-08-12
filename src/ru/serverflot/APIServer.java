package ru.serverflot;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIServer {
    ThreadPoolExecutor threadPoolExecutor;

    public APIServer(String Host, int Port) {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(Host, Port), 0);
            server.createContext("/test", new  MyHttpHandler());
            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(threadPoolExecutor);
            server.start();
            System.out.println(" Server started on " + Host + ":" + Port);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}

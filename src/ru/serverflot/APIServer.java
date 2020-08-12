package ru.serverflot;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class APIServer {
    private HttpServer server;

    public APIServer(String Host, int Port) {
        try {
            server = HttpServer.create(new InetSocketAddress(Host, Port), 0);
            System.out.println("Started server at " + Host + ":" + Port);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}

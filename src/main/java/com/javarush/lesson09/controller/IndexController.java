package com.javarush.lesson09.controller;

import com.javarush.lesson09.ChatServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Objects;

public class IndexController extends AbstractController {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream indexData = ChatServer.class.getResourceAsStream(("index.html"));
        try (indexData) {
            String path = exchange.getRequestURI().getPath();
            Object content = path.equals("/")
                    ? new String(Objects.requireNonNull(indexData).readAllBytes())
                    : "404 Not Found";
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            int rCode = path.equals("/") ? HttpURLConnection.HTTP_OK : HttpURLConnection.HTTP_NOT_FOUND;
            sendResponse(exchange, rCode, content);
        }
    }


}

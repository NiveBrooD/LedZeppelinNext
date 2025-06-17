package com.javarush.lesson09.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

abstract class AbstractController implements HttpHandler {
    protected void sendResponse(HttpExchange exchange, int rCode, Object content) throws IOException {
        exchange.sendResponseHeaders(rCode, content.toString().getBytes().length);
        exchange.getResponseBody().write(content.toString().getBytes());
        exchange.getResponseBody().close();
    }
}

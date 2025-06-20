package com.javarush.lesson09.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.lesson09.model.Message;
import com.javarush.lesson09.service.MessageService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;

public class MessageController extends AbstractController {
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public MessageController(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String id = exchange.getRequestURI().getPath().replace("/messages/", "");
        switch (requestMethod) {
            case "GET" -> {
                Object content = id.isEmpty()
                        ? messageService.getAll().values()
                        : messageService.get(Long.parseLong(id));
                String json = objectMapper.writeValueAsString(content);
                sendResponse(exchange, HttpURLConnection.HTTP_OK, json);
            }
            case "POST" -> {
                Message message = objectMapper.readValue(exchange.getRequestBody(), Message.class);
                boolean result = messageService.post(message);
                int rCode = result
                        ? HttpURLConnection.HTTP_CREATED
                        : HttpURLConnection.HTTP_BAD_REQUEST;
                sendResponse(exchange, rCode, objectMapper.writeValueAsString(message));
            }

            case "PUT" -> {
                Message message = objectMapper.readValue(exchange.getRequestBody(), Message.class);
                boolean result = messageService.put(message);
                int rCode = result
                        ? HttpURLConnection.HTTP_ACCEPTED
                        : HttpURLConnection.HTTP_BAD_REQUEST;
                sendResponse(exchange, rCode, objectMapper.writeValueAsString(message));
            }

            case "DELETE" -> {
                Message message = messageService.get(Long.parseLong(id));
                boolean result = messageService.delete(message);
                int rCode = result
                        ? HttpURLConnection.HTTP_OK
                        : HttpURLConnection.HTTP_BAD_REQUEST;
                sendResponse(exchange, rCode, result);
            }
            default -> sendResponse(exchange, HttpURLConnection.HTTP_BAD_METHOD, "");
        }

    }
}

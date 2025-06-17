package com.javarush.lesson09;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.lesson09.controller.IndexController;
import com.javarush.lesson09.controller.MessageController;
import com.javarush.lesson09.repository.MessageRepository;
import com.javarush.lesson09.service.MessageService;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

public class ChatServer {

    public static final int PORT = 1234;
    public static final String LOCALHOST = "localhost";

    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(LOCALHOST, PORT);
        HttpServer server = HttpServer.create(inetSocketAddress, 0);
        ObjectMapper objectMapper = new ObjectMapper();
        MessageRepository repository = new MessageRepository();
        MessageService messageService = new MessageService(repository);
        Map<String, HttpHandler> controllers = Map.of(
                "/", new IndexController(),
                "/messages/", new MessageController(messageService, objectMapper)
        );
        controllers.forEach(server::createContext);
        server.start();
        System.out.println("Server started on http://localhost:" + server.getAddress().getPort());
    }


}



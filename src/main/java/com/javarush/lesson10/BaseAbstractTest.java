package com.javarush.lesson10;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.lesson09.ChatServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class BaseAbstractTest {

    public static final String ROOT = "http://localhost:1234/";
    protected static HttpClient httpClient;
    protected static ObjectMapper objectMapper;

    @BeforeAll
    public static void beforeAll() throws IOException, InterruptedException {
        objectMapper = new ObjectMapper();
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(2))
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT))
                .build();
        try {
            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException e) {
            ChatServer.main(new String[0]);
        }
    }

    @AfterAll
    public static void afterAll() {
        httpClient.close();
    }
}

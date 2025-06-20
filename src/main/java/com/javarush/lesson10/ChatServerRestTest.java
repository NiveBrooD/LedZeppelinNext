package com.javarush.lesson10;

import com.fasterxml.jackson.core.type.TypeReference;
import com.javarush.lesson09.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatServerRestTest extends BaseAbstractTest {

    @Test
    @DisplayName("When get all then count more zero")
    void whenGetAllThenCountMoreZero() throws IOException, InterruptedException {
        //given
        HttpRequest getAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/"))
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();
        //when
        HttpResponse<String> httpResponse = httpClient
                .send(getAllRequest, HttpResponse.BodyHandlers.ofString());
        //then
        List<Message> messages = objectMapper.readValue(httpResponse.body(), new TypeReference<List<Message>>() {
        });
        assertEquals(200, httpResponse.statusCode());
        Assertions.assertFalse(messages.isEmpty());
    }

    @Test
    @DisplayName("When get message by id then id correct")
    void whenGetMessageByIdThenIdCorrect() throws IOException, InterruptedException {
        long id = 1L;
        HttpRequest getAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/" + id))
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();
        HttpResponse<String> httpResponse = httpClient
                .send(getAllRequest, HttpResponse.BodyHandlers.ofString());
        Message message = objectMapper.readValue(httpResponse.body(), Message.class);
        assertEquals(200, httpResponse.statusCode());
        assertEquals(id, message.getId());
    }


    @Test
    @DisplayName("When create new message then correct id and content")
    void whenCreateNewMessageThenCorrectIdAndContent() throws IOException, InterruptedException {
        String json = """
                {"text" : "create from postman",  "author" : "Postman"}
                """;
        HttpRequest getAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .timeout(Duration.ofSeconds(2))
                .build();
        HttpResponse<String> httpResponse = httpClient
                .send(getAllRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, httpResponse.statusCode());
        Message message = objectMapper.readValue(httpResponse.body(), Message.class);
        assertEquals("create from postman", message.getText());
        assertTrue(message.getId() > 0);
    }


    @Test
    @DisplayName("When get and update message then correct text")
    void whenGetAndUpdateMessageThenCorrectText() throws IOException, InterruptedException {
        long id = 1L;
        HttpRequest getAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/" + id))
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();
        HttpResponse<String> httpResponse = httpClient
                .send(getAllRequest, HttpResponse.BodyHandlers.ofString());
        Message message = objectMapper.readValue(httpResponse.body(), Message.class);
        message.setAuthor("author after Put");
        String textAfterPut = "text after Put";
        message.setText(textAfterPut);

        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/"))
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(message)))
                .timeout(Duration.ofSeconds(2))
                .build();
        httpResponse = httpClient
                .send(putRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(202, httpResponse.statusCode());
        Message messageUpdated = objectMapper.readValue(httpResponse.body(), Message.class);
        assertEquals(textAfterPut, messageUpdated.getText());
    }


    @Test
    @DisplayName("When create and delete message then status 2xx")
    void whenCreateAndDeleteMessageThenStatus2Xx() throws IOException, InterruptedException {
        String json = """
                {"text" : "create for delete",  "author" : "HttpClient"}
                """;
        HttpRequest getAllRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .timeout(Duration.ofSeconds(2))
                .build();
        HttpResponse<String> httpResponse = httpClient
                .send(getAllRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, httpResponse.statusCode());
        Message message = objectMapper.readValue(httpResponse.body(), Message.class);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(ROOT + "messages/" + message.getId()))
                .DELETE()
                .build();
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, httpResponse.statusCode());
    }

}

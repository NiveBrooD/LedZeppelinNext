package com.javarush.lesson10;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ChatServerHomePageTest extends BaseAbstractTest{



    @Test
    @DisplayName("When load index page then html contains closed body tag")
    void whenLoadIndexPageThenHtmlContainsClosedBodyTag() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:1234/"))
                .timeout(Duration.ofSeconds(1))
                .GET()
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int code = httpResponse.statusCode();
        String body = httpResponse.body();
        Assertions.assertEquals(200, code);
        Assertions.assertTrue(body.contains("</body>"));
    }


}

package com.devsecops.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests d'integration : l'application demarre sur un port aleatoire et on l'appelle en HTTP. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TasksApplicationTests {

    @Value("${local.server.port}")
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();

    private HttpResponse<String> send(String method, String path, String json) throws IOException, InterruptedException {
        HttpRequest.BodyPublisher body = json == null
                ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .method(method, body)
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void homePageResponds() throws Exception {
        assertThat(send("GET", "/", null).statusCode()).isEqualTo(200);
    }

    @Test
    void createListAndCompleteTask() throws Exception {
        HttpResponse<String> created = send("POST", "/api/tasks", "{\"title\":\"Tester le pipeline\"}");
        assertThat(created.statusCode()).isEqualTo(201);

        assertThat(send("GET", "/api/tasks", null).body()).contains("Tester le pipeline");
        assertThat(send("PUT", "/api/tasks/1/done", null).statusCode()).isEqualTo(200);
    }

    @Test
    void invalidInputIsRejected() throws Exception {
        assertThat(send("POST", "/api/tasks", "{\"title\":\"\"}").statusCode()).isEqualTo(400);
        assertThat(send("PUT", "/api/tasks/999/done", null).statusCode()).isEqualTo(404);
    }
}

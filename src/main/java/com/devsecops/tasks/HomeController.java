package com.devsecops.tasks;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Page d'accueil, utilisee par le test "curl" du pipeline. */
@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="fr">
                <head><meta charset="utf-8"><title>Tasks API</title></head>
                <body>
                  <h1>Tasks API - projet DevSecOps</h1>
                  <p><a href="/api/tasks">/api/tasks</a></p>
                </body>
                </html>
                """;
    }
}

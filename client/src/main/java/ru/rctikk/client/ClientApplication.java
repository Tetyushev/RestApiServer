package ru.rctikk.client;

import ru.rctikk.server.dto.Tensor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/hello"))
                .GET()
                .build();
        Tensor tensor = Tensor.random(2, 3);

        HttpResponse<String> httpResponse = httpClient.send(
                httpRequest,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("Response: " + httpResponse.body());
    }
}
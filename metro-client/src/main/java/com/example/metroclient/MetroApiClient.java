package com.example.metroclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class MetroApiClient {

    private final String baseUrl = "http://localhost:8081/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private String username;
    private String password;

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String authHeader() {
        String auth = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        return "Basic " + encoded;
    }

    public List<StationClientDto> loadStations() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/stations"))
                .header("Authorization", authHeader())
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Ошибка загрузки станций: " + response.statusCode());
        }
        return mapper.readValue(response.body(), new TypeReference<List<StationClientDto>>(){});
    }

    public RouteClientResponse findRoute(Long fromId, Long toId) throws IOException, InterruptedException {
        String url = baseUrl + "/route?fromId=" + fromId + "&toId=" + toId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", authHeader())
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Ошибка построения маршрута: " + response.statusCode());
        }
        return mapper.readValue(response.body(), RouteClientResponse.class);
    }
}

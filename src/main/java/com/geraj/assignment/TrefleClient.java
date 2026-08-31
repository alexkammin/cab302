package com.geraj.assignment;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TrefleClient {
    private final HttpClient httpClient;
    private final String apiToken;
    private static final String BASE_URL = "https://trefle.io";

    public TrefleClient(HttpClient httpClient, String apiToken) {
        if (apiToken == null || apiToken.isBlank()) {
            throw new IllegalArgumentException("API token must not be null or blank");
        }
        this.httpClient = httpClient;
        this.apiToken = apiToken;
    }

    public String getPlant(int plantId) throws Exception {
        String url = String.format("%s/api/v1/plants/%d", BASE_URL, plantId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();

        if (statusCode == 404) {
            throw new IllegalArgumentException("Plant not found with ID: " + plantId);
        }
        else if (statusCode >= 400) {
            throw new RuntimeException(String.format("Trefle API returned error %d: %s", statusCode, response.body()));
        }

        return response.body();
    }

    public String findPlant(String query) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = String.format("%s/api/v1/plants/search?q=%s", BASE_URL, encodedQuery);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();

        if (statusCode >= 400) {
            throw new RuntimeException(String.format("Trefle API returned error %d: %s", statusCode, response.body()));
        }

        return response.body();
    }
}

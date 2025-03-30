package com.example.antockassignment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class HttpApiClient {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T sendGetRequest(String urlString, Class<T> responseType) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        BufferedReader br = (responseCode >= 200 && responseCode <= 300)
                ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
                : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        conn.disconnect();

        return objectMapper.readValue(response.toString(), responseType);
    }
}

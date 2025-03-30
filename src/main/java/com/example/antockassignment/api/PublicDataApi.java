package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.PublicData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class PublicDataApi {

    @Value("${public-data.open-api.base-url}")
    private String BASE_URL;
    @Value("${public-data.open-api.service-key}")
    private String API_KEY;

    private final ApiClient apiClient;

    public PublicData getPublicData(String brno) throws Exception {
        String url = BASE_URL
                + "?serviceKey=" + URLEncoder.encode(API_KEY, StandardCharsets.UTF_8)
                + "&pageNo=1&numOfRows=1&resultType=json&brno=" + brno;

        return apiClient.get(url, PublicData.class);
    }
}
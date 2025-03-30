package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.PublicData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
public class PublicDataApi {

    @Value("${public-data.open-api.base-url}")
    private String BASE_URL;
    @Value("${public-data.open-api.service-key}")
    private String API_KEY;

    private final HttpApiClient httpApiClient;

    public PublicData getPublicData(String brno) throws Exception {
        String url = BASE_URL
                + "?serviceKey=" + URLEncoder.encode(API_KEY, "UTF-8")
                + "&pageNo=1&numOfRows=1&resultType=json&brno=" + brno;

        return httpApiClient.get(url, PublicData.class);
    }
}
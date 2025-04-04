package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.pubilcAddress.PublicAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class BusinessJusoApi {

    @Value("${juso.open-api.base-url}")
    private String BASE_URL;
    @Value("${juso.open-api.service-key}")
    private String API_KEY;

    private final ApiClient apiClient;

    public PublicAddress getBusinessJusoData(String keyword) throws Exception {
        String url = BASE_URL + "1&countPerPage=1"
                + "&keyword=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8)
                + "&confmKey=" + API_KEY
                + "&resultType=json";

        return apiClient.get(url, PublicAddress.class);
    }
}

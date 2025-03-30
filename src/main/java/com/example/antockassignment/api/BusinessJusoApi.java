package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.pubilcAddress.PublicAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
public class BusinessJusoApi {

    @Value("${juso.open-api.base-url}")
    private String BASE_URL;
    @Value("${juso.open-api.service-key}")
    private String API_KEY;

    private final HttpApiClient httpApiClient;

    public PublicAddress getBusinessJusoData(String keyword) throws Exception {
        String url = BASE_URL + "1&countPerPage=1"
                + "&keyword=" + URLEncoder.encode(keyword, "UTF-8")
                + "&confmKey=" + API_KEY
                + "&resultType=json";

        return httpApiClient.get(url, PublicAddress.class);
    }
}

package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.PublicData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PublicDataApi {
    private static final String BASE_URL = "http://apis.data.go.kr/1130000/MllBsDtl_2Service/getMllBsInfoDetail_2";
    private static final String API_KEY = "";

    public PublicData getPublicData(String brno) throws IOException {
        String urlString = BASE_URL +"?serviceKey="+ URLEncoder.encode(API_KEY, "UTF-8") +"&pageNo=1&numOfRows=1&resultType=json&brno="+ brno;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        int responseCode = conn.getResponseCode();
        BufferedReader rd = (responseCode >= 200 && responseCode <= 300)
                ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
                : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();
        conn.disconnect();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.toString(), PublicData.class);
    }


}
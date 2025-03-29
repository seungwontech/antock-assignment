package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.pubilcAddress.PublicAddress;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class BusinessJusoApi {

    private static final String BASE_URL = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=";
    private static final String API_KEY = "";


    public PublicAddress getBusinessJusoData(String keyword) {
        try {
            // OPEN API 호출 URL 정보 설정
            String apiUrl = BASE_URL
                    + "1&countPerPage=1"
                    + "&keyword=" + URLEncoder.encode(keyword, "UTF-8")
                    + "&confmKey=" + API_KEY
                    + "&resultType=json";
            System.out.println(apiUrl);
            // URL 연결 및 BufferedReader 설정
            URL url = new URL(apiUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String tempStr;

            // 응답 결과 읽기
            while ((tempStr = br.readLine()) != null) {
                sb.append(tempStr); // 응답 결과 JSON 저장
            }
            br.close();

            // 응답 결과를 BusinessData 객체로 변환하여 반환

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(sb.toString(), PublicAddress.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 예외 처리 시 null 반환
        }
    }
}

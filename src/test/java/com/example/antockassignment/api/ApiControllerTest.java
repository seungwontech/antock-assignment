package com.example.antockassignment.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("공공 데이터 API 통신 테스트")
    public void getPublicData() throws Exception {
        String brno = "3848603874";  // 예시 사업자 번호
        this.mvc.perform(get("/v1/api/public-data/{brno}", brno))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("주소 API 통신 테스트")
    public void jusoOpenApi() throws Exception {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.put("keyword", Collections.singletonList("서울 도봉구"));

        this.mvc.perform(get("/v1/api/business-juso")
                .params(param))  // .params()에서 Map을 사용
                .andExpect(status().isOk())
                .andDo(print());
    }
}
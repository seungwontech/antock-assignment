package com.example.antockassignment.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CSVDownloadControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("CSV 다운로드 API 통신 테스트")
    public void getCSVData() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/v1/api/csv-download")
                .contentType("application/json")
                .content("{\"city\": \"서울특별시\", \"district\": \"도봉구\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> {
                    System.out.println("Response: " + result.getResponse().getContentAsString());
                });
    }
}
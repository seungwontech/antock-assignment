package com.example.antockassignment.api;

import com.example.antockassignment.api.dto.PublicData;
import com.example.antockassignment.api.dto.pubilcAddress.PublicAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/api")
public class ApiController {

    private final PublicDataApi publicDataApi;
    private final BusinessJusoApi businessJusoApi;

    // 공공 데이터 조회
    @GetMapping("/public-data/{brno}")
    public PublicData getPublicData(@PathVariable String brno) throws Exception {
        return publicDataApi.getPublicData(brno);
    }

    // 사업자 주소 조회
    @GetMapping("/business-juso")
    public PublicAddress getBusinessJusoData(@RequestParam String keyword) throws Exception {
        return businessJusoApi.getBusinessJusoData(keyword);
    }

}

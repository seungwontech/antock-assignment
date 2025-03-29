package com.example.antockassignment.business.controller;

import com.example.antockassignment.business.repository.BusinessRepository;
import com.example.antockassignment.selenium.CSVDownload;
import com.example.antockassignment.selenium.dto.CSVData;
import com.example.antockassignment.selenium.util.CsvToJsonConverter;
import com.example.antockassignment.business.dto.*;
import com.example.antockassignment.api.BusinessJusoApi;
import com.example.antockassignment.business.service.BusinessService;
import com.example.antockassignment.api.PublicDataApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final PublicDataApi publicDataApi;
    private final CSVDownload csvDownload;
    private final BusinessJusoApi businessJusoApi;
    private final BusinessService businessService;
    private final BusinessRepository businessRepository;


    @PostMapping("/csv-data")
    public ResponseEntity<Void> getCSVData(@RequestBody CityDistrictRequest request) throws IOException, InterruptedException {
        String csvContent = csvDownload.download(request.city(), request.district());
        List<CSVData> csvDatas = CsvToJsonConverter.convertCsvToList(csvContent);

        businessService.create(csvDatas);

        return ResponseEntity.ok().build();
    }

}

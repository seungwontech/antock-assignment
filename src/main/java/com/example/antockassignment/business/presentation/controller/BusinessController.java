package com.example.antockassignment.business.presentation.controller;

import com.example.antockassignment.selenium.CSVDownload;
import com.example.antockassignment.selenium.dto.CSVData;
import com.example.antockassignment.selenium.util.CsvToJsonConverter;
import com.example.antockassignment.business.presentation.dto.*;
import com.example.antockassignment.business.domin.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
public class BusinessController {

    private final CSVDownload csvDownload;
    private final BusinessService businessService;


    @PostMapping("/csv-data")
    public ResponseEntity<Void> getCSVData(@RequestBody CityDistrictRequest request) throws Exception {
        String csvContent = csvDownload.download(request.city(), request.district());
        List<CSVData> csvDatas = CsvToJsonConverter.convertCsvToList(csvContent);
        businessService.create(csvDatas);
        return ResponseEntity.ok().build();
    }

}

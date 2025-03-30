package com.example.antockassignment.selenium;

import com.example.antockassignment.business.presentation.dto.CityDistrictRequest;
import com.example.antockassignment.selenium.dto.CSVData;
import com.example.antockassignment.selenium.util.CsvToJsonConverter;
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
public class CSVDownloadController {

    private final CSVDownload csvDownload;

    @PostMapping("/csv-download")
    public ResponseEntity<List<CSVData>> getCSVData(@RequestBody CityDistrictRequest request) throws Exception {
        String csvContent = csvDownload.download(request.city(), request.district());
        List<CSVData> csvDatas = CsvToJsonConverter.convertCsvToList(csvContent);
        return ResponseEntity.ok(csvDatas);
    }

}

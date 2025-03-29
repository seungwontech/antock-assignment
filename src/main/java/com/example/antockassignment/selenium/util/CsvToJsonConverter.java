package com.example.antockassignment.selenium.util;

import com.example.antockassignment.selenium.dto.CSVData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CsvToJsonConverter {

    public static List<CSVData> convertCsvToList(String csvContent) {
        List<CSVData> result = new ArrayList<>();

        // CSV 데이터를 줄 단위로 분리
        String[] rows = csvContent.split("\n");

        // 각 행을 처리
        for (String row : rows) {
            // 쉼표로 구분된 컬럼 데이터
            String[] columns = row.split(",");

            // 컬럼이 10개 이상일 경우에만 처리
            if (columns.length > 9) {
                // CsvRowData 생성
                CSVData data = CSVData.create(columns[0], columns[1], columns[3], columns[9], columns[4]);

                // 법인 데이터만 필터링
                if (data.isCorporation()) {
                    result.add(data);
                }
            }
        }

        return result;
    }
}

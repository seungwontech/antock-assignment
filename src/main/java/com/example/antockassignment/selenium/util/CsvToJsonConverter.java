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

        String[] rows = csvContent.split("\n");

        for (String row : rows) {
            String[] columns = row.split(",");

            if (columns.length > 9) {
                CSVData data = CSVData.create(columns[0], columns[1], columns[3], columns[9], columns[4]);

                if (data.isCorporation()) {
                    result.add(data);
                }
            }
        }

        return result;
    }
}

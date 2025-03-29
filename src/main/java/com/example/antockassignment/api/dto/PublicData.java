package com.example.antockassignment.api.dto;

import java.util.List;

public record PublicData(
        String resultCode,
        String resultMsg,
        int numOfRows,
        int pageNo,
        int totalCount,
        List<PublicDataItem> items
) {
}
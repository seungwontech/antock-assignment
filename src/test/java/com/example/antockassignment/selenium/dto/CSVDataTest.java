package com.example.antockassignment.selenium.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CSVDataTest {

    @Test
    public void testIsCorporation_whenCorporation_thenTrue() {
        CSVData csvData = CSVData.create("123", "Some Company", "123-456-789", "Some Address", "법인");
        assertTrue(csvData.isCorporation(), "법인일 경우 isCorporation()은 true여야 합니다.");
    }

    @Test
    public void testIsCorporation_whenNotCorporation_thenFalse() {
        CSVData csvData = CSVData.create("123", "Another Company", "987-654-321", "Another Address", "개인");
        assertFalse(csvData.isCorporation(), "법인이 아닐 경우 isCorporation()은 false여야 합니다.");
    }

    @Test
    public void testIsLctnAddrNumeric_whenNumericAddress_thenTrue() {
        CSVData csvData = CSVData.create("123", "Company", "111-222-333", "12345", "법인");
        assertTrue(csvData.isLctnAddrNumeric(), "주소가 숫자로만 구성되어 있으면 isLctnAddrNumeric()은 true여야 합니다.");
    }

    @Test
    public void testIsLctnAddrNumeric_whenNonNumericAddress_thenFalse() {
        CSVData csvData = CSVData.create("123", "Company", "111-222-333", "서울시 강남구", "법인");
        assertFalse(csvData.isLctnAddrNumeric(), "주소가 숫자가 아니면 isLctnAddrNumeric()은 false여야 합니다.");
    }
}
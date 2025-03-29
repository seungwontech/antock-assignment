package com.example.antockassignment.selenium.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record CSVData(String prmmiMnno, String bzmnNm, String brno, String lctnAddr, String corpYnNm) {
    public static CSVData create(String prmmiMnno, String bzmnNm, String brno, String lctnAddr, String corpYnNm) {
        return new CSVData(prmmiMnno, bzmnNm, brno.replaceAll("-", ""), lctnAddr, corpYnNm);
    }

    @JsonIgnore
    public boolean isCorporation() {
        return "법인".equals(corpYnNm);
    }

    @JsonIgnore
    public boolean isLctnAddrNumeric() {
        return lctnAddr != null && lctnAddr.chars().allMatch(Character::isDigit);
    }
}

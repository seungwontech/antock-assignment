package com.example.antockassignment.business.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id")
    private Long id;

    @Column(nullable = false)
    private String prmmiMnno; // 통신판매번호

    @Column(nullable = false)
    private String bzmnNm; // 상호

    @Column(nullable = false)
    private String brno; // 사업자등록번호

    private String crno; // 법인등록번호

    private String admCd; // 행정구역코드

    public static Business create(String prmmiMnno, String bzmnNm, String brno, String crno, String admCd) {
        return new Business(null, prmmiMnno, bzmnNm, brno, crno, admCd);
    }
}

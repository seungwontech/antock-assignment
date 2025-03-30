package com.example.antockassignment.business.domin.repository;

import com.example.antockassignment.business.domin.entity.Business;

import java.util.List;

public interface BusinessRepository {
    void saveAll(List<Business> businesses);
}

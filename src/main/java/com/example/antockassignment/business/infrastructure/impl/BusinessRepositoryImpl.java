package com.example.antockassignment.business.infrastructure.impl;

import com.example.antockassignment.business.domin.entity.Business;
import com.example.antockassignment.business.domin.repository.BusinessRepository;
import com.example.antockassignment.business.infrastructure.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BusinessRepositoryImpl implements BusinessRepository {
    private final BusinessJpaRepository businessJpaRepository;

    @Override
    public void saveAll(List<Business> businesses) {
        businessJpaRepository.saveAll(businesses);
    }
}

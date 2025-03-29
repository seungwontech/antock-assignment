package com.example.antockassignment.business.repository;

import com.example.antockassignment.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}

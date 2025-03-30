package com.example.antockassignment.business.infrastructure;

import com.example.antockassignment.business.domin.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessJpaRepository extends JpaRepository<Business, Long> {
}

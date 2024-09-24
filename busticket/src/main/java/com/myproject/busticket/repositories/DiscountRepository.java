package com.myproject.busticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.busticket.models.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    Discount findByCode(String code);
}

package com.example.projekt.repository;

import com.example.projekt.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findTop5ByOrderByDateAsc();
}

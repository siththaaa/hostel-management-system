package com.example.Hostel.System.repository;

import com.example.Hostel.System.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    long countByStatus(String status);
}
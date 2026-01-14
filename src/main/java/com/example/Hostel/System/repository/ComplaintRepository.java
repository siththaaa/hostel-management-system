package com.example.Hostel.System.repository;


import com.example.Hostel.System.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    long countByStatus(String status);
}
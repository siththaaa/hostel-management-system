package com.example.Hostel.System.repository;

import com.example.Hostel.System.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    long countByStatus(String status);
}

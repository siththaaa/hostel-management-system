package com.example.Hostel.System.repository;

import com.example.Hostel.System.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    long countByStatus(String status);
    @Query("SELECT s FROM Student s WHERE s.name NOT IN " +
            "(SELECT r.student.name FROM Room r WHERE r.student IS NOT NULL)")


    List<Student> findUnassignedStudents();
}


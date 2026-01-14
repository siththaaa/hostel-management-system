package com.example.Hostel.System.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idNumber;
    private String name;
    private String email;
    private String phone;
    private String dob;
    private String guardianName;
    private String guardianCell;
    private String year;
    private String status;
}

package com.example.Hostel.System.controller;

import com.example.Hostel.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PaymentRepository feeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/reports")
    public String showReports(Model model) {
        long activeStudents = studentRepository.countByStatus("Active");
        long inactiveStudents = studentRepository.countByStatus("Inactive");

        // Fetch room status counts
        long occupiedRooms = roomRepository.countByStatus("Occupied");
        long availableRooms = roomRepository.countByStatus("Available");



        model.addAttribute("activeCount", activeStudents);
        model.addAttribute("inactiveCount", inactiveStudents);
        model.addAttribute("occupiedRooms", occupiedRooms);
        model.addAttribute("availableRooms", availableRooms);

        model.addAttribute("paidFees", feeRepository.countByStatus("Paid"));
        model.addAttribute("pendingFees", feeRepository.countByStatus("Pending"));

        model.addAttribute("pendingComplaints", complaintRepository.countByStatus("Pending"));
        model.addAttribute("inProgressComplaints", complaintRepository.countByStatus("In Progress"));
        model.addAttribute("resolvedComplaints", complaintRepository.countByStatus("Resolved"));

        model.addAttribute("scheduledEvents", eventRepository.countByStatus("Scheduled"));
        model.addAttribute("pendingEvents", eventRepository.countByStatus("Pending"));

        return "reports";
    }
}
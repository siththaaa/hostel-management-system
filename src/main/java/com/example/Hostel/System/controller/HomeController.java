package com.example.Hostel.System.controller;


import com.example.Hostel.System.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping({"", "/"})
    public String home() {
        return "index";
    }




    @Autowired
    private StudentRepository studentRepository;

     @Autowired
    private RoomRepository roomRepository;



    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch counts for the cards
        model.addAttribute("studentCount", studentRepository.count());
        model.addAttribute("roomCount", roomRepository.count());
        model.addAttribute("feeCount", paymentRepository.count());
        model.addAttribute("complaintCount",complaintRepository.count());
        model.addAttribute("eventCount",eventRepository.count());

        return "dashboard";
    }











    // You should also ensure your dashboard mapping is correct:



}


package com.example.Hostel.System.controller;

import com.example.Hostel.System.models.Complaint;
import com.example.Hostel.System.models.Room;
import com.example.Hostel.System.repository.ComplaintRepository;
import com.example.Hostel.System.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/add-complaint")
    public String showForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "addcomplaint";
    }

    @PostMapping("/save-complaint")
    public String saveComplaint(@RequestParam String roomNumber,
                                @RequestParam String description,
                                @RequestParam String status,
                                RedirectAttributes ra) {

        Complaint complaint = new Complaint();
        complaint.setRoomNumber(roomNumber);
        complaint.setDescription(description);
        complaint.setStatus(status);

        complaintRepository.save(complaint);
        ra.addFlashAttribute("message", "Complaint created successfully!");

        return "redirect:/complaint";
    }

    @GetMapping("/complaint")
    public String listComplaints(Model model) {
        List<Complaint> complaints = complaintRepository.findAll();
        model.addAttribute("complaints", complaints);
        return "complaint"; // Must match your templates/complaint.html filename exactly
    }

    @GetMapping("/edit-complaint/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid complaint Id:" + id));
        model.addAttribute("complaint", complaint);
        return "update-complaint";
    }

    @PostMapping("/update-complaint/{id}")
    public String updateComplaint(@PathVariable("id") Long id,
                                  @ModelAttribute("complaint") Complaint complaint,
                                  RedirectAttributes ra) { // Added RedirectAttributes here
        complaint.setId(id);
        complaintRepository.save(complaint);
        ra.addFlashAttribute("message", "Complaint updated successfully!");
        return "redirect:/complaint";
    }

    @GetMapping("/delete-complaint/{id}")
    public String deleteComplaint(@PathVariable("id") Long id,
                                  RedirectAttributes ra) { // Added RedirectAttributes here
        complaintRepository.deleteById(id);
        ra.addFlashAttribute("message", "Complaint deleted successfully!");
        return "redirect:/complaint";
    }
}
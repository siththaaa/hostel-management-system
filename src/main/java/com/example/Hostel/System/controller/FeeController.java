package com.example.Hostel.System.controller;

import com.example.Hostel.System.models.Payment;
import com.example.Hostel.System.models.Room;
import com.example.Hostel.System.repository.PaymentRepository;
import com.example.Hostel.System.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FeeController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/fees")
    public String showFeePage(Model model) {

        List<Payment> paymentList = paymentRepository.findAll();


        model.addAttribute("payments", paymentList);

        return "fee";
    }

    @GetMapping("/add-payment")
    public String showPaymentForm(Model model) {

        model.addAttribute("rooms", roomRepository.findAll());
        return "addpayment";
    }

    @GetMapping("/get-student-by-room/{roomNumber}")
    @ResponseBody
    public String getStudent(@PathVariable String roomNumber) {

        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room != null && room.getStudent() != null) {
            return room.getStudent().getName();
        }
        return "No Student Assigned";
    }

    @PostMapping("/save-payment")
    public String savePayment(@ModelAttribute("payment") Payment payment, RedirectAttributes ra) {
        paymentRepository.save(payment);
        ra.addFlashAttribute("success", "Payment recorded!");
        return "redirect:/fees";
    }

    @GetMapping("/editFee/{id}")
    public String showUpdateFeeForm(@PathVariable("id") Long id, Model model) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + id));


        model.addAttribute("payment", payment);

        model.addAttribute("rooms", roomRepository.findAll());

        return "updatefee";
    }


    @PostMapping("/update-payment/{id}")
    public String updatePayment(@PathVariable("id") Long id, @ModelAttribute("payment") Payment payment, RedirectAttributes ra) {

        payment.setId(id);
        paymentRepository.save(payment);
        ra.addFlashAttribute("success", "Fee updated successfully!");
        return "redirect:/fees";
    }

    @GetMapping("/deleteFee/{id}")
    public String deleteFee(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            paymentRepository.deleteById(id);
            ra.addFlashAttribute("success", "Fee record deleted successfully!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error deleting record: " + e.getMessage());
        }
        return "redirect:/fees";
    }

    

}

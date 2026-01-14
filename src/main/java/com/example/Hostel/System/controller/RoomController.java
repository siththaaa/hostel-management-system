package com.example.Hostel.System.controller;

import com.example.Hostel.System.models.Room;
import com.example.Hostel.System.models.Student;
import com.example.Hostel.System.repository.RoomRepository;
import com.example.Hostel.System.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    // 1. Show the Room Management Table
    @GetMapping("/room")
    public String roomPage(Model model) {
        List<Room> roomList = roomRepository.findAll();
        model.addAttribute("rooms", roomList);
        return "room";
    }

    // 2. Show the Add Room Form
    @GetMapping("/addroom")
    public String addRoomPage(Model model) {
        // 1. Fetch all students and all rooms
        List<Student> allStudents = studentRepository.findAll();
        List<Room> allRooms = roomRepository.findAll();

        // 2. Find IDs of students who already have a room
        List<Long> assignedStudentIds = allRooms.stream()
                .filter(r -> r.getStudent() != null)
                .map(r -> r.getStudent().getId())
                .collect(Collectors.toList());

        // 3. Filter the list: only show students NOT in the assigned list
        List<Student> availableStudents = allStudents.stream()
                .filter(s -> !assignedStudentIds.contains(s.getId()))
                .collect(Collectors.toList());

        model.addAttribute("students", availableStudents);
        model.addAttribute("room", new Room());
        return "addroom";
    }


    @PostMapping("/saveRoom")
    public String saveRoom(@ModelAttribute("room") Room room, RedirectAttributes redirectAttributes) {
        String message = (room.getId() != null) ? "Room updated successfully!" : "Room created successfully!";
        roomRepository.save(room);
        redirectAttributes.addFlashAttribute("success", message);
        return "redirect:/room";
    }


    @GetMapping("/editRoom/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room Id:" + id));

        // 1. Get IDs of students assigned to OTHER rooms (excluding this one)
        List<Long> otherAssignedIds = roomRepository.findAll().stream()
                .filter(r -> r.getStudent() != null && !r.getId().equals(id))
                .map(r -> r.getStudent().getId())
                .collect(Collectors.toList());

        // 2. Filter all students: include those not in the "otherAssignedIds" list
        List<Student> availableStudents = studentRepository.findAll().stream()
                .filter(s -> !otherAssignedIds.contains(s.getId()))
                .collect(Collectors.toList());

        model.addAttribute("room", room);
        model.addAttribute("students", availableStudents);
        return "updateroom";
    }


    @GetMapping("/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        roomRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Room deleted successfully!");
        return "redirect:/room";
    }


    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public String getStudentNameByRoom(String roomNumber) {
        Room room = roomRepository.findByRoomNumber(roomNumber);
        if (room != null && room.getStudent() != null) {
            return room.getStudent().getName();
        }
        return "No Student Assigned";
    }
}
package com.example.Hostel.System.controller;

import com.example.Hostel.System.models.Room;
import com.example.Hostel.System.repository.RoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.example.Hostel.System.models.Student;
import com.example.Hostel.System.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class StudentController {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;


    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "student";
    }



    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        studentRepository.save(student);


        redirectAttributes.addFlashAttribute("message", "Student added successfully!");

        return "redirect:/students";
    }
    @GetMapping("/addstudent")
    public String showAddStudentForm(HttpServletRequest request) {


        String referer = request.getHeader("Referer");

        if (referer != null && referer.contains("/students")) {
            return "addstudent";
        }

        return "redirect:/students";
    }


    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes ra) {
        // 1. Find the student
        Student student = studentRepository.findById(id).orElse(null);

        if (student != null) {
            // 2. Find any room assigned to this student
            Room assignedRoom = roomRepository.findByStudent(student);

            // 3. If a room is found, unassign the student and mark room as Available
            if (assignedRoom != null) {
                assignedRoom.setStudent(null);
                assignedRoom.setStatus("Available");
                roomRepository.save(assignedRoom);
            }

            // 4. Now safely delete the student
            studentRepository.deleteById(id);
            ra.addFlashAttribute("success", "Student deleted and room unassigned successfully!");
        }

        return "redirect:/students";
    }

    // Update student
    @GetMapping("/edit-student/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));

        model.addAttribute("student", student);
        return "update-student"; // This should be a new HTML file 'update-student.html'
    }

    // update student
    @PostMapping("/update-student/{id}")
    public String updateStudent(@PathVariable("id") long id, @ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        student.setId(id);
        studentRepository.save(student);
        // Add success message for update
        redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
        return "redirect:/students";
    }


}

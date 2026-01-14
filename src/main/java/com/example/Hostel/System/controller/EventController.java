package com.example.Hostel.System.controller;


import com.example.Hostel.System.models.Event;
import com.example.Hostel.System.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;




@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;


    @GetMapping("/event")
    public String listEvents(Model model) {

        List<Event> eventList = eventRepository.findAll();


        model.addAttribute("events", eventList);

        return "event";
    }

    @GetMapping("/add-event-form")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event()); // Bind an empty object to the form
        return "addevent";
    }




    @PostMapping("/saveEvent")
    public String saveEvent(@ModelAttribute("event") Event event, RedirectAttributes ra) {

        boolean isUpdate = (event.getId() != null);


        eventRepository.save(event);


        String message = isUpdate ? "Event updated successfully!" : "Event created successfully!";
        ra.addFlashAttribute("message", message);

        return "redirect:/event";
    }

    @GetMapping("/editEvent/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // 1. Fetch the event from the database using the ID
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));

        // 2. Pass the event data to the updateevent.html page
        model.addAttribute("event", event);

        return "updateevent";
    }

    @GetMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable("id") Long id, RedirectAttributes ra) {
        // 1. Delete the event from the database
        eventRepository.deleteById(id);

        // 2. Add the delete message using the 'ra' parameter
        ra.addFlashAttribute("message", "Event deleted successfully!");

        return "redirect:/event";
    }

}

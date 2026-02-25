package com.netgroup.netgroup_server.controller;

import com.netgroup.netgroup_server.domain.Event;
import com.netgroup.netgroup_server.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/get/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add/event")
    public ResponseEntity<Event> createEvent(@RequestBody @Valid Event event){
        eventService.save(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
}

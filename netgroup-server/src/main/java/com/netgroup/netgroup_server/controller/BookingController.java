package com.netgroup.netgroup_server.controller;

import com.netgroup.netgroup_server.domain.Booking;
import com.netgroup.netgroup_server.domain.Event;
import com.netgroup.netgroup_server.model.BookingRequest;
import com.netgroup.netgroup_server.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Event> book(@RequestBody @Valid BookingRequest request){
        Booking booking = getBookingService().getBookingFromRequest(request);
        getBookingService().save(booking);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public BookingService getBookingService() {
        return bookingService;
    }
}

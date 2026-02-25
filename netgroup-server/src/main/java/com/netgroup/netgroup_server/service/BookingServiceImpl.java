package com.netgroup.netgroup_server.service;

import com.netgroup.netgroup_server.dao.BookingDao;
import com.netgroup.netgroup_server.dao.EventDao;
import com.netgroup.netgroup_server.domain.Booking;
import com.netgroup.netgroup_server.domain.Event;
import com.netgroup.netgroup_server.exception.EventNotFoundException;
import com.netgroup.netgroup_server.exception.UserAlreadyBookedException;
import com.netgroup.netgroup_server.model.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private EventDao eventDao;

    @Override
    public Booking getBookingFromRequest(BookingRequest bookingRequest) {
        Event e = eventDao.getById(bookingRequest.getId(), Event.class);
        if (e == null) {
            throw new EventNotFoundException("event with id " + bookingRequest.getId() + " not found");
        }
        Booking booking = new Booking();
        booking.setEvent(e);
        booking.setFirstName(bookingRequest.getFirstName());
        booking.setLastName(bookingRequest.getLastName());
        booking.setPersonalCode(bookingRequest.getPersonalCode());
        return booking;
    }

    @Override
    public void save(Booking booking) {
        Booking b = bookingDao.getByPersonalCodeAndEvent(booking.getPersonalCode(), booking.getEvent().getId());
        if(b == null){
            bookingDao.save(booking);
            return;
        }
        throw new UserAlreadyBookedException("Person with code " + booking.getPersonalCode() + " already booked the event");
    }
}

package com.netgroup.netgroup_server.service;

import com.netgroup.netgroup_server.domain.Booking;
import com.netgroup.netgroup_server.model.BookingRequest;

public interface BookingService {
     void save(Booking booking);
     Booking getBookingFromRequest(BookingRequest bookingRequest);
}

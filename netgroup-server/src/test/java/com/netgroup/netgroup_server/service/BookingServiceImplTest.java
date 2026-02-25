package com.netgroup.netgroup_server.service;

import com.netgroup.netgroup_server.dao.BookingDao;
import com.netgroup.netgroup_server.dao.EventDao;
import com.netgroup.netgroup_server.domain.Booking;
import com.netgroup.netgroup_server.domain.Event;
import com.netgroup.netgroup_server.exception.EventNotFoundException;
import com.netgroup.netgroup_server.exception.UserAlreadyBookedException;
import com.netgroup.netgroup_server.model.BookingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookingServiceImpl Tests")
public class BookingServiceImplTest {

    @Mock
    private BookingDao bookingDao;

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingRequest bookingRequest;
    private Event event;
    private Booking booking;

    @BeforeEach
    void setUp() {
        bookingRequest = new BookingRequest();
        bookingRequest.setId(1L);
        bookingRequest.setFirstName("John");
        bookingRequest.setLastName("Doe");
        bookingRequest.setPersonalCode("12345678");

        event = new Event();
        event.setId(1L);
        event.setEventName("Test Event");

        booking = new Booking();
        booking.setEvent(event);
        booking.setFirstName("John");
        booking.setLastName("Doe");
        booking.setPersonalCode("12345678");
    }

    @Test
    @DisplayName("Should create booking from request with all fields populated")
    void testGetBookingFromRequest_Success() {
        when(eventDao.getById(1L, Event.class)).thenReturn(event);

        Booking result = bookingService.getBookingFromRequest(bookingRequest);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("12345678", result.getPersonalCode());
        assertEquals(event, result.getEvent());
        verify(eventDao, times(1)).getById(1L, Event.class);
    }

    @Test
    @DisplayName("Should throw exception when event not found")
    void testGetBookingFromRequest_EventNotFound() {
        when(eventDao.getById(1L, Event.class)).thenReturn(null);

        assertThrows(EventNotFoundException.class, () -> {
            bookingService.getBookingFromRequest(bookingRequest);
        });
        verify(eventDao, times(1)).getById(1L, Event.class);
    }

    @Test
    @DisplayName("Should map all booking request fields correctly")
    void testGetBookingFromRequest_FieldMapping() {
        bookingRequest.setFirstName("Jane");
        bookingRequest.setLastName("Smith");
        bookingRequest.setPersonalCode("87654321");
        when(eventDao.getById(1L, Event.class)).thenReturn(event);

        Booking result = bookingService.getBookingFromRequest(bookingRequest);

        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("87654321", result.getPersonalCode());
    }

    @Test
    @DisplayName("Should save booking when user has not already booked the event")
    void testSave_NewBooking_Success() {
        when(bookingDao.getByPersonalCodeAndEvent("12345678", 1L)).thenReturn(null);

        bookingService.save(booking);

        verify(bookingDao, times(1)).getByPersonalCodeAndEvent("12345678", 1L);
        verify(bookingDao, times(1)).save(booking);
    }

    @Test
    @DisplayName("Should throw UserAlreadyBookedException when user already booked the event")
    void testSave_UserAlreadyBooked_ThrowsException() {
        Booking existingBooking = new Booking();
        existingBooking.setPersonalCode("12345678");
        when(bookingDao.getByPersonalCodeAndEvent("12345678", 1L)).thenReturn(existingBooking);

        UserAlreadyBookedException exception = assertThrows(UserAlreadyBookedException.class, () -> {
            bookingService.save(booking);
        });

        assertEquals("Person with code 12345678 already booked the event", exception.getMessage());
        verify(bookingDao, times(1)).getByPersonalCodeAndEvent("12345678", 1L);
        verify(bookingDao, never()).save(any());
    }

    @Test
    @DisplayName("Should not save booking when duplicate exists")
    void testSave_DuplicateBooking_NoSave() {
        Booking existingBooking = new Booking();
        when(bookingDao.getByPersonalCodeAndEvent("12345678", 1L)).thenReturn(existingBooking);

        assertThrows(UserAlreadyBookedException.class, () -> {
            bookingService.save(booking);
        });
        verify(bookingDao, never()).save(booking);
    }

    @Test
    @DisplayName("Should include personal code in exception message")
    void testSave_ExceptionMessageContainsPersonalCode() {
        String personalCode = "99999999";
        booking.setPersonalCode(personalCode);
        Booking existingBooking = new Booking();
        when(bookingDao.getByPersonalCodeAndEvent(personalCode, 1L)).thenReturn(existingBooking);

        UserAlreadyBookedException exception = assertThrows(UserAlreadyBookedException.class, () -> {
            bookingService.save(booking);
        });

        assertTrue(exception.getMessage().contains(personalCode));
    }

    @Test
    @DisplayName("Should save multiple different users for the same event")
    void testSave_MultipleUsersForSameEvent() {
        Booking booking1 = new Booking();
        booking1.setEvent(event);
        booking1.setPersonalCode("11111111");

        Booking booking2 = new Booking();
        booking2.setEvent(event);
        booking2.setPersonalCode("22222222");

        when(bookingDao.getByPersonalCodeAndEvent("11111111", 1L)).thenReturn(null);
        when(bookingDao.getByPersonalCodeAndEvent("22222222", 1L)).thenReturn(null);

        bookingService.save(booking1);
        bookingService.save(booking2);

        verify(bookingDao, times(2)).save(any());
        verify(bookingDao).save(booking1);
        verify(bookingDao).save(booking2);
    }

    @Test
    @DisplayName("Should verify correct parameters passed to DAO method")
    void testSave_VerifyDAOCallParameters() {
        when(bookingDao.getByPersonalCodeAndEvent("12345678", 1L)).thenReturn(null);

        bookingService.save(booking);

        verify(bookingDao).getByPersonalCodeAndEvent("12345678", 1L);
    }
}
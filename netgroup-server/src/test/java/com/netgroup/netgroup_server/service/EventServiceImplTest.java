package com.netgroup.netgroup_server.service;

import com.netgroup.netgroup_server.dao.EventDao;
import com.netgroup.netgroup_server.domain.Event;
import com.netgroup.netgroup_server.exception.EventAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventServiceImpl Tests")
public class EventServiceImplTest {

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event event;
    private Date eventDate;

    @BeforeEach
    void setUp() {
        eventDate = new Date();

        event = new Event();
        event.setId(1L);
        event.setEventName("Test Event");
        event.setEventDate(eventDate);
    }

    @Test
    @DisplayName("Should return all events when events exist")
    void testFindAll_Success() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        Event event2 = new Event();
        event2.setId(2L);
        event2.setEventName("Another Event");
        eventList.add(event2);

        when(eventDao.getEvents()).thenReturn(eventList);

        List<Event> result = eventService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Event", result.get(0).getEventName());
        assertEquals("Another Event", result.get(1).getEventName());
        verify(eventDao, times(1)).getEvents();
    }

    @Test
    @DisplayName("Should return empty list when no events exist")
    void testFindAll_EmptyList() {
        List<Event> emptyList = new ArrayList<>();
        when(eventDao.getEvents()).thenReturn(emptyList);

        List<Event> result = eventService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(eventDao, times(1)).getEvents();
    }

    @Test
    @DisplayName("Should return list with single event")
    void testFindAll_SingleEvent() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        when(eventDao.getEvents()).thenReturn(eventList);

        List<Event> result = eventService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(event, result.get(0));
    }

    @Test
    @DisplayName("Should save event when event with same date does not exist")
    void testSave_NewEvent_Success() {
        when(eventDao.getEventByDate(eventDate)).thenReturn(null);

        eventService.save(event);

        verify(eventDao, times(1)).getEventByDate(eventDate);
        verify(eventDao, times(1)).save(event);
    }

    @Test
    @DisplayName("Should throw EventAlreadyExistsException when event with same date exists")
    void testSave_DuplicateEvent_ThrowsException() {
        Event existingEvent = new Event();
        existingEvent.setId(2L);
        existingEvent.setEventDate(eventDate);
        when(eventDao.getEventByDate(eventDate)).thenReturn(existingEvent);

        EventAlreadyExistsException exception = assertThrows(EventAlreadyExistsException.class, () -> {
            eventService.save(event);
        });

        assertEquals("Event with date: " + eventDate + " already exists", exception.getMessage());
        verify(eventDao, times(1)).getEventByDate(eventDate);
        verify(eventDao, never()).save(any());
    }

    @Test
    @DisplayName("Should not save duplicate event")
    void testSave_DuplicateEvent_NoSave() {
        Event existingEvent = new Event();
        when(eventDao.getEventByDate(eventDate)).thenReturn(existingEvent);

        assertThrows(EventAlreadyExistsException.class, () -> {
            eventService.save(event);
        });
        verify(eventDao, never()).save(event);
    }

    @Test
    @DisplayName("Should include event date in exception message")
    void testSave_ExceptionMessageContainsDate() {
        Event existingEvent = new Event();
        when(eventDao.getEventByDate(eventDate)).thenReturn(existingEvent);

        EventAlreadyExistsException exception = assertThrows(EventAlreadyExistsException.class, () -> {
            eventService.save(event);
        });

        assertTrue(exception.getMessage().contains(eventDate.toString()));
    }

    @Test
    @DisplayName("Should save multiple events with different dates")
    void testSave_MultipleEventsWithDifferentDates() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse("2026/3/15 12:00");
            date2 = formatter.parse("2026/4/20 12:00");
        }catch (ParseException e){
            fail("could not parse date");
        }

        Event event1 = new Event();
        event1.setEventDate(date1);

        Event event2 = new Event();
        event2.setEventDate(date2);

        when(eventDao.getEventByDate(date1)).thenReturn(null);
        when(eventDao.getEventByDate(date2)).thenReturn(null);

        eventService.save(event1);
        eventService.save(event2);

        verify(eventDao, times(2)).save(any());
        verify(eventDao).save(event1);
        verify(eventDao).save(event2);
    }

    @Test
    @DisplayName("Should verify correct date parameter passed to DAO")
    void testSave_VerifyDAOCallParameters() {
        when(eventDao.getEventByDate(eventDate)).thenReturn(null);

        eventService.save(event);

        verify(eventDao).getEventByDate(eventDate);
    }

    @Test
    @DisplayName("Should save event with different properties")
    void testSave_EventWithDifferentProperties() {
        Event customEvent = new Event();
        customEvent.setId(5L);
        customEvent.setEventName("Custom Event");
        Date eventDate = new Date();
        customEvent.setEventDate(eventDate);

        when(eventDao.getEventByDate(eventDate)).thenReturn(null);

        eventService.save(customEvent);

        verify(eventDao, times(1)).save(customEvent);
    }

    // Tests for findById() method

    @Test
    @DisplayName("Should find event by id when event exists")
    void testFindById_Success() {
        when(eventDao.getById(1L, Event.class)).thenReturn(event);

        Event result = eventService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Event", result.getEventName());
        verify(eventDao, times(1)).getById(1L, Event.class);
    }

    @Test
    @DisplayName("Should return null when event does not exist")
    void testFindById_EventNotFound() {
        when(eventDao.getById(999L, Event.class)).thenReturn(null);

        Event result = eventService.findById(999L);

        assertNull(result);
        verify(eventDao, times(1)).getById(999L, Event.class);
    }

    @Test
    @DisplayName("Should find event by correct id")
    void testFindById_CorrectIdUsed() {
        Event event2 = new Event();
        event2.setId(2L);
        event2.setEventName("Another Event");

        when(eventDao.getById(2L, Event.class)).thenReturn(event2);

        Event result = eventService.findById(2L);

        assertEquals(2L, result.getId());
        assertEquals("Another Event", result.getEventName());
        verify(eventDao).getById(2L, Event.class);
    }

    @Test
    @DisplayName("Should return event with all properties populated")
    void testFindById_AllPropertiesPopulated() {
        Date testDate = new Date();
        Event fullEvent = new Event();
        fullEvent.setId(3L);
        fullEvent.setEventName("Full Event");
        fullEvent.setEventDate(testDate);

        when(eventDao.getById(3L, Event.class)).thenReturn(fullEvent);

        Event result = eventService.findById(3L);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("Full Event", result.getEventName());
        assertEquals(testDate, result.getEventDate());
    }

    @Test
    @DisplayName("Should verify correct parameters passed to findById DAO method")
    void testFindById_VerifyDAOCallParameters() {
        when(eventDao.getById(1L, Event.class)).thenReturn(event);

        eventService.findById(1L);

        verify(eventDao).getById(1L, Event.class);
    }

    @Test
    @DisplayName("Should handle finding event by zero id")
    void testFindById_ZeroId() {
        when(eventDao.getById(0L, Event.class)).thenReturn(null);

        Event result = eventService.findById(0L);

        assertNull(result);
        verify(eventDao).getById(0L, Event.class);
    }

    @Test
    @DisplayName("Should handle finding event by negative id")
    void testFindById_NegativeId() {
        when(eventDao.getById(-1L, Event.class)).thenReturn(null);

        Event result = eventService.findById(-1L);

        assertNull(result);
        verify(eventDao).getById(-1L, Event.class);
    }
}
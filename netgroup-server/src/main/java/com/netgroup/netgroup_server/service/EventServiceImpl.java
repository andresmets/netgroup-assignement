package com.netgroup.netgroup_server.service;

import com.netgroup.netgroup_server.dao.EventDao;
import com.netgroup.netgroup_server.domain.Event;
import com.netgroup.netgroup_server.exception.EventAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Override
    public List<Event> findAll() {
        return eventDao.getEvents();
    }
    @Override
    public void save(Event event) {
        Event existing = eventDao.getEventByDate(event.getEventDate());
        if (existing != null) {
            throw new EventAlreadyExistsException("Event with date: " + event.getEventDate() + " already exists");
        }
        eventDao.save(event);
    }

    @Override
    public Event findById(long id) {
        return eventDao.getById(id, Event.class);
    }
}

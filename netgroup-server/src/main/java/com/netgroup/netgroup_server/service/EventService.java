package com.netgroup.netgroup_server.service;

import com.netgroup.netgroup_server.domain.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();
    Event findById(long id);
    void save(Event event);
}

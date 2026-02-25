package com.netgroup.netgroup_server.dao;

import com.netgroup.netgroup_server.domain.Event;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class EventDao extends AbstractDao<Event> {

    public List<Event> getEvents() {
        Query q = getEntityManager().createNamedQuery("loadEvents");
        return q.getResultList();
    }

    public Event getEventByDate(Date date) {
        Query q = getEntityManager().createNamedQuery("loadEventByDate");
        q.setParameter("eventDate", date);
        List<Event> events = q.getResultList();
        if(events.isEmpty()){
            return null;
        }
        return events.getFirst();
    }
}

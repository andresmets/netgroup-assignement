package com.netgroup.netgroup_server.dao;

import com.netgroup.netgroup_server.domain.Booking;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingDao extends AbstractDao<Booking> {

    public Booking getByPersonalCodeAndEvent(String personalCode, Long eventId) {
        Query q = getEntityManager().createNamedQuery("loadBookingByPersonalCode");
        q.setParameter("code", personalCode);
        q.setParameter("eventId", eventId);
        List<Booking> result = q.getResultList();
        if (result != null && !result.isEmpty()) {
            return result.getFirst();
        }
        return null;
    }
}

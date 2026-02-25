package com.netgroup.netgroup_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@jakarta.persistence.Entity
@NamedQuery(name="loadBookingByPersonalCode", query = "from Booking b join fetch b.event e where b.personalCode = :code and e.id = :eventId order by b.id")
public class Booking extends Entity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "personalCode")
    private String personalCode;
    @ManyToOne
    private Event event;
}


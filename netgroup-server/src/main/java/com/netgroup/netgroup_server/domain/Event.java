package com.netgroup.netgroup_server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@jakarta.persistence.Entity
@NamedQuery(name="loadEvents", query = "from Event order by id")
@NamedQuery(name="loadEventByDate", query = "from Event e where e.eventDate = :eventDate order by e.id")
public class Event extends Entity {
    @NotEmpty(message = "event name is required")
    @Column(name = "name")
    private String eventName;
    @Column(name = "event_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date eventDate;
    @NotNull(message = "amount of people is required")
    @Column(name = "amount")
    private Integer amount;
}

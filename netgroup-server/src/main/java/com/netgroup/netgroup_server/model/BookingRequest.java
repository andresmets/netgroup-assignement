package com.netgroup.netgroup_server.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    @NotNull(message = "Event id is required")
    private Long id;
    @NotEmpty(message = "first name is required")
    private String firstName;
    @NotEmpty(message = "last name is required")
    private String lastName;
    @NotEmpty(message = "personal code is required")
    private String personalCode;
}

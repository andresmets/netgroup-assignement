package com.netgroup.netgroup_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "authorities")
public class Authorities extends Entity {
    @Column(name = "username")
    private String username;
    @Column(name = "authority")
    private String authority;
}

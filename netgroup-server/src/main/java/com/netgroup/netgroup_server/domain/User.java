package com.netgroup.netgroup_server.domain;

import jakarta.persistence.*;

import java.util.List;

@jakarta.persistence.Entity
@Table(name="users")
@NamedQuery(name="userByPrincipal", query = "from User u where u.username = :username")
public class User extends Entity {
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @OneToMany
    @JoinColumn(name = "username", referencedColumnName = "username")
    private List<Authorities> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }
}

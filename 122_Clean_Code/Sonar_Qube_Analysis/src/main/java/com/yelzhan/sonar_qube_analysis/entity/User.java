package com.yelzhan.sonar_qube_analysis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CODE SMELL: Public fields instead of private with getters/setters
    public String username;
    public String password;
    public String email;
    public String role;

    // BUG: Missing equals() and hashCode() for JPA entity

    // CODE SMELL: Empty constructor with no documentation
    public User() {
    }

    // CODE SMELL: Constructor with too many parameters
    public User(Long id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters and setters (some missing for demonstration)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // CODE SMELL: Inconsistent getter/setter naming
    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}
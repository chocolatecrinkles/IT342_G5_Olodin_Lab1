package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(unique = true, nullable = false)
    String email;

    @Column(name = "passwordHash", nullable = false)
    String passwordHash;

    @Column(name = "first_name")
    String firstname;

    @Column(name = "last_name")
    String lastname;

    @Column(name = "create_at")
    LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getUserId() { 
        return userId; 
    }

    public String getEmail() { 
        return email; 
    }

    public String getPasswordHash() { 
        return passwordHash; 
    }

    public String getFirstname() { 
        return firstname; 
    }

    public String getLastname() { 
        return lastname; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    
    }
    public void setFirstname(String firstname) { 
        this.firstname = firstname; 
    }

    public void setLastname(String lastname) { 
        this.lastname = lastname; 
    }
}

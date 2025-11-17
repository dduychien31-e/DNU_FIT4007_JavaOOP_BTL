package com.student.management.domain.person;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected String id;
    protected String fullName;
    protected String email;
    protected String phone;

    public Person(String id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
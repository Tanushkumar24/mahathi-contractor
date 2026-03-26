package com.mahathi.contractor.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactForm {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number must be 10 digits")
    private String phone;
    
    private String service;
    
    private String location;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    private String submittedAt;

    public ContactForm() {
    }

    public ContactForm(String name, String email, String phone, String service, 
                       String location, String message, String submittedAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.service = service;
        this.location = location;
        this.message = message;
        this.submittedAt = submittedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "ContactForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", service='" + service + '\'' +
                ", location='" + location + '\'' +
                ", message='" + message + '\'' +
                ", submittedAt='" + submittedAt + '\'' +
                '}';
    }
}

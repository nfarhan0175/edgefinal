package com.example.edgefinal;

public class UserDetails {
    private String username;
    private String email;
    private String contact;

    public UserDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(UserDetails.class)
    }

    public UserDetails(String username, String email, String contact) {
        this.username = username;
        this.email = email;
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}

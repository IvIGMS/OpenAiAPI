package com.ivanfrias.myapi.Dto;

public class UsersDTONoPass {

    public UsersDTONoPass(Long id, String email, String name, String lastname) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
    }

    public UsersDTONoPass() {
    }

    private Long id;
    private String email;
    private String name;
    private String lastname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}


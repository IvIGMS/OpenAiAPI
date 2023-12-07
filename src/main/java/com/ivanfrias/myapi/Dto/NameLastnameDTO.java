package com.ivanfrias.myapi.Dto;

public class NameLastnameDTO {
    private String name;
    private String lastname;

    public NameLastnameDTO(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public NameLastnameDTO() {
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

package com.ivanfrias.myapi.Dto;

public class UsersDTONoPass {

    public UsersDTONoPass(Long id, String email, String name, String lastname, double budget, String teamName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.budget = budget;
        this.teamName = teamName;
    }

    public UsersDTONoPass() {
    }

    private Long id;
    private String email;
    private String name;
    private String lastname;
    private String teamName;
    private double budget;


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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}


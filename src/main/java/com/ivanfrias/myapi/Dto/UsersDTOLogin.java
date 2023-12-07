package com.ivanfrias.myapi.Dto;

public class UsersDTOLogin {

    private String email;
    private String password;

    public UsersDTOLogin() {
    }

    public UsersDTOLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

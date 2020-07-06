package com.example.englishtester.model;

public class Account {
    private String email;
    private String userName;
    private String pass;

    public Account() {
    }

    public Account(String email, String userName, String pass) {
        this.email = email;
        this.userName = userName;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

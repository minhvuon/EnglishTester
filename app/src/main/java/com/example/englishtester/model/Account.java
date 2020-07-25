package com.example.englishtester.model;

public class Account {
    private String id;
    private String email;
    private String userName;
    private String pass;


    public Account() {
    }

    public Account(String id, String email, String userName, String pass) {
        this.email = email;
        this.userName = userName;
        this.pass = pass;
    }
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
package com.example.wanna_bet70;

public class Account {
    String name;
    String email;
    String passward;
    String phone;

    public Account(String name, String email, String passward, String phone) {
        this.name = name;
        this.email = email;
        this.passward = passward;
        this.phone = phone;
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

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

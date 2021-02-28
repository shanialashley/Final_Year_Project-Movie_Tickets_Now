package com.example.authapp.Model;

public class User {
    public String fname, lname, eml;

    public User(){

    }

    public User(String first_name, String last_name, String e_mail){
        this.fname = first_name;
        this.lname = last_name;
        this.eml = e_mail;
    }
}

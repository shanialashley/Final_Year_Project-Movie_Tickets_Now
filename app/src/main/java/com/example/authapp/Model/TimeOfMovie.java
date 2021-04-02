package com.example.authapp.Model;

public class TimeOfMovie {

    private String t;
    private String type;

    public TimeOfMovie(String t, String type ) {
        this.t = t;
        this.type = type;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

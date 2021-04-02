package com.example.authapp.Model;

public class DateOfWeek {

    private String date_num;
    private String date_week;
    private String date;

    public DateOfWeek(String date_num, String date_week, String date) {
        this.date_num = date_num;
        this.date_week = date_week;
        this.date = date;
    }

    public String getDate_num() {
        return date_num;
    }

    public void setDate_num(String date_num) {
        this.date_num = date_num;
    }

    public String getDate_week() {
        return date_week;
    }

    public void setDate_week(String date_week) {
        this.date_week = date_week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

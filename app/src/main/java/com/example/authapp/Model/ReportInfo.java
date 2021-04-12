package com.example.authapp.Model;

public class ReportInfo {

    String title;
    String date;
    String time;
    int num_of_tickets;
    String theater;
    String location;
    String movie_type;

    public  ReportInfo(){}

    public ReportInfo(String title, String date, String time, int num_of_tickets, String theater, String location, String movie_type) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.num_of_tickets = num_of_tickets;
        this.theater = theater;
        this.location = location;
        this.movie_type = movie_type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum_of_tickets() {
        return num_of_tickets;
    }

    public void setNum_of_tickets(int num_of_tickets) {
        this.num_of_tickets = num_of_tickets;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMovie_type() {
        return movie_type;
    }

    public void setMovie_type(String movie_type) {
        this.movie_type = movie_type;
    }
}

package com.example.authapp.Model;

public class QRCode {

    String theater_title, title, date, time, ticket_type, id_code;

    public QRCode(String theater_title, String title, String date, String time, String ticket_type) {
        this.theater_title = theater_title;
        this.title = title;
        this.date = date;
        this.time = time;
        this.ticket_type = ticket_type;
    }

    public QRCode() {
    }

    public QRCode(String theater_title, String title, String date, String time, String ticket_type, String id_code) {
        this.theater_title = theater_title;
        this.title = title;
        this.date = date;
        this.time = time;
        this.ticket_type = ticket_type;
        this.id_code = id_code;
    }

    public String getTheater_title() {
        return theater_title;
    }

    public void setTheater_title(String theater_title) {
        this.theater_title = theater_title;
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

    public String getTicket_type() {
        return ticket_type;
    }

    public void setTicket_type(String ticket_type) {
        this.ticket_type = ticket_type;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }
}

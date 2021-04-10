package com.example.authapp.Model;

import java.io.Serializable;

public class Movies implements Serializable {
    private String title;
    private String description;
    private String genre;
    private String thumbnail_url;
    private String length;
    private String rating;
    private String starring;
    private String directors;
    private String trailer_link;
    private String cover_url;
    private String type;

    public Movies(){}

    public Movies(String title, String description, String genre, String thumbnail_url, String length, String rating, String starring, String directors, String trailer_link, String cover_url, String type) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.thumbnail_url = thumbnail_url;
        this.length = length;
        this.rating = rating;
        this.starring = starring;
        this.directors = directors;
        this.trailer_link = trailer_link;
        this.cover_url = cover_url;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getTrailer_link() {
        return trailer_link;
    }

    public void setTrailer_link(String trailer_link) {
        this.trailer_link = trailer_link;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

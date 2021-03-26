package com.example.authapp.Model;

public class Movie {

    private String title;
    private String description;
    private String genre;
    private String thumbn;
    private String cover;
    private String link;
    private int thumbnail;
    private String length;
    private String rating;
    private String starring;
    private String directors;
    private int trailerLink;
    private int coverPhoto;
    private String category;

    public Movie( int thumbnail, String title, int coverPhoto) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;
    }

    public Movie(int thumbnail, String title) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Movie(String title, String description, int thumbnail, int coverPhoto, String genre, String length, String rating, String starring, String directors, int trailerLink, String type) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;
        this.length = length;
        this.rating = rating;
        this.starring = starring;
        this.directors = directors;
        this.trailerLink = trailerLink;
        this.category = type;

    }

    public Movie(String title, String description, String genre, String thumbn, String cover, String link, String length, String rating, String starring, String directors, String category) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.thumbn = thumbn;
        this.cover = cover;
        this.link = link;
        this.length = length;
        this.rating = rating;
        this.starring = starring;
        this.directors = directors;
        this.category = category;
    }

    public int getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(int coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getLength() {
        return length;
    }

    public String getRating() {
        return rating;
    }

    public String getStarring() {
        return starring;
    }

    public String getDirectors() {
        return directors;
    }

    public int getTrailerLink() {
        return trailerLink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public void setTrailerLink(int trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

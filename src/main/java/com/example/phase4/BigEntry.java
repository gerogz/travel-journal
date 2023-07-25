package com.example.phase4;

public class BigEntry {
    private String date;
    private String city;
    private String country;
    private int rating;
    private String note;

    public BigEntry(String date, String city, String country, int rating, String note) {
        this.date = date;
        this.city = city;
        this.country = country;
        this.rating = rating;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

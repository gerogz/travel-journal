package com.example.phase4;

public class CityEntries {
    private String date;
    private int rating;
    private String note;

    private String user;

    public CityEntries(String date, int rating, String note, String user) {
        this.date = date;
        this.rating = rating;
        this.note = note;
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String toString() {
        return date + " " + rating + " " + note;
    }
}

package com.example.phase4;

public class adminPageEntry {
    private String user;
    private String email;
    private String city;
    private String note;
    private String reason;
    private String flagger;
    private String date;
    private int rating;
    private String flaggerEmail;
    private int locationId;

    public String getEmail() {
        return email;
    }

    public String getFlaggerEmail() {
        return flaggerEmail;
    }

    public int getLocationId() {
        return locationId;
    }

    public adminPageEntry(String user, String city, String note, String reasons, String flagger, String date, int rating, String email, String flaggerEmail, int locationId) {
        this.user = user;
        this.email = email;
        this.city = city;
        this.note = note;
        this.reason = reasons;
        this.flagger = flagger;
        this.date = date;
        this.rating = rating;
        this.flaggerEmail = flaggerEmail;
        this.locationId = locationId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reasons) {
        this.reason = reasons;
    }

    public String getFlagger() {
        return flagger;
    }

    public void setFlagger(String flagger) {
        this.flagger = flagger;
    }

    public int getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }
}

package com.example.phase4;

public class City {
    private String name;
    private String country;
    private double avg;

    public City(String name, String country, double avg) {
        this.name = name;
        this.country = country;
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
}
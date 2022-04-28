package com.example.finalyearproject;

public class Ride {
    private String location;
    private String time;
    private String email;
    private String seats;
    private int day;
    private int month;
    private int year;

    public Ride() {

    }

    public Ride(String location, String time, String email, String seats, int day, int month, int year) {
        this.location = location;
        this.time = time;
        this.email = email;
        this.seats = seats;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}

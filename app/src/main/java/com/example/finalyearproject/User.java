package com.example.finalyearproject;

public class User {
    private String name, dateOfBirth, email, phone;
    private boolean hasCar;

    public User(){

    }

    public User(String fullName, String age, String email, String phone, boolean hasCar){
        this.name  = name;
        this.dateOfBirth = dateOfBirth;
        this.email=email;
        this.phone = phone;
        this.hasCar = hasCar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isHasCar() {
        return hasCar;
    }

    public void setHasCar(boolean hasCar) {
        this.hasCar = hasCar;
    }
}

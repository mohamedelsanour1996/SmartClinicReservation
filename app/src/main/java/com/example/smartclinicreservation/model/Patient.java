package com.example.smartclinicreservation.model;

public class Patient {
    String position;
    String name;
    String age;
    String gender;

    public Patient() {
    }
    public Patient( String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Patient(String position, String name, String age, String gender) {
        this.position=position;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

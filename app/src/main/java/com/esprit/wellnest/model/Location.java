package com.esprit.wellnest.model;

public class Location {
    private int Id;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public Location(){

    }

    public Location(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return this.location

                ;
    }
}

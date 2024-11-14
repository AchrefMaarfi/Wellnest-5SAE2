package com.esprit.wellnest.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@TypeConverters({Converters.class})
    @Entity(tableName = "Hotel")
    public class Hotel implements Serializable {
    @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") // Define column name for ID
        private int id;
        @ColumnInfo(name = "hotelName")
        private String hotelName;
        @ColumnInfo(name = "pricePeriod")
        private String pricePeriod;
        @ColumnInfo(name = "location")
        private String location;
        private int numberSeat;

        private double price;
    @ColumnInfo(name = "reservedSeats")
    private List<String> reservedSeats;

    // Constructor, getters, and setters for reservedSeats

    public List<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getPricePeriod() {
        return pricePeriod;
    }

    public void setPricePeriod(String pricePeriod) {
        this.pricePeriod = pricePeriod;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public int getId() {
        return id;
    }

    public int getNumberSeat() {
        return numberSeat;
    }

    public void setNumberSeat(int numberSeat) {
        this.numberSeat = numberSeat;
    }


    public Hotel() {
        this.reservedSeats = new ArrayList<>(); // Initialize the list
    }
    @Ignore
    public Hotel(String hotelName, double price, String location) {
        this.hotelName = hotelName;
        this.price = price;
        this.location = location;
        this.reservedSeats = new ArrayList<>();
    }

    public Hotel(String hotelName, double price, String pricePeriod, String location, int numberSeat) {

        this.hotelName = hotelName;
        this.price = price;
        this.pricePeriod = pricePeriod;
        this.location = location;
        this.numberSeat = numberSeat;
        this.reservedSeats = new ArrayList<>();
    }

}

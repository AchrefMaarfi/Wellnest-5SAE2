package com.esprit.wellnest.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

@Entity(tableName = "Reservation")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "userID")
    public int userID;

    @ColumnInfo(name = "hotelID")
    public int hotelID;

    @ColumnInfo(name = "reservedSeats")
    private List<String> reservedSeats;


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public Reservation(int userID, int hotelID) {
        this.userID = userID;
        this.hotelID = hotelID;
        this.reservedSeats = new ArrayList<>();

    }


    public List<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }
}

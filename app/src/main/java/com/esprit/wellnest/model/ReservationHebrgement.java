package com.esprit.wellnest.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ReservationHebrgement")
public class ReservationHebrgement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "userID")
    public int userID;

    @ColumnInfo(name = "hebergementID")
    public int hebergementID;

    @ColumnInfo(name = "dateResrvation")
    public Date dateResrvation;

    @ColumnInfo(name = "datefinResrvation")
    public Date datefinResrvation;

    @ColumnInfo(name = "datedebutResrvation")
    public Date datedebutResrvation;

    // Constructor used by Room (with userID and hebergementID)
    //public ReservationHebrgement(int userID, int hebergementID) {
      //  this.userID = userID;
       // this.hebergementID = hebergementID;
       // this.dateResrvation = new Date(); // Automatically set to current date
    //}

    public ReservationHebrgement(int userID, int hebergementID) {
        this.userID = userID;
        this.hebergementID = hebergementID;
    }
@Ignore
    public ReservationHebrgement(int userID, int hebergementID, Date dateResrvation, Date datefinResrvation, Date datedebutResrvation) {
        this.userID = userID;
        this.hebergementID = hebergementID;
        this.dateResrvation = dateResrvation;
        this.datefinResrvation = datefinResrvation;
        this.datedebutResrvation = datedebutResrvation;
    }

    // Getters and setters for all fields

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

    public int getHebergementID() {
        return hebergementID;
    }

    public void setHebergementID(int hebergementID) {
        this.hebergementID = hebergementID;
    }

    public Date getDateResrvation() {
        return dateResrvation;
    }

    public void setDateResrvation(Date dateResrvation) {
        this.dateResrvation = dateResrvation;
    }

    public Date getDatefinResrvation() {
        return datefinResrvation;
    }

    public void setDatefinResrvation(Date datefinResrvation) {
        this.datefinResrvation = datefinResrvation;
    }

    public Date getDatedebutResrvation() {
        return datedebutResrvation;
    }

    public void setDatedebutResrvation(Date datedebutResrvation) {
        this.datedebutResrvation = datedebutResrvation;
    }
}

package com.esprit.wellnest.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.esprit.wellnest.service.Converters;

import java.util.Date;
import java.util.List;


@Entity(tableName = "events")
public class Event implements Parcelable {
    @ColumnInfo(name = "event_id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String adresse;
    private int capacity;
    private Date startDate;
    private Date endDate;
    private Double prix;
    @TypeConverters(Converters.class)
    private List<String> photoPaths;
    private double latitude;
    private double longitude;
    @Embedded
    private User user;

    // Constructors
    public Event() {}

    public Event(User user, double longitude, double latitude, List<String> photoPaths, Double prix, Date startDate, Date endDate, int capacity, String adresse, String description, String title, int id) {
        this.user = user;
        this.longitude = longitude;
        this.latitude = latitude;
        this.photoPaths = photoPaths;
        this.prix = prix;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.adresse = adresse;
        this.description = description;
        this.title = title;
        this.id = id;
    }

    // Parcelable implementation
    protected Event(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        adresse = in.readString();
        capacity = in.readInt();
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        photoPaths = in.createStringArrayList();
        prix = in.readDouble();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(adresse);
        dest.writeInt(capacity);
        dest.writeLong(startDate.getTime());
        dest.writeLong(endDate.getTime());
        dest.writeStringList(photoPaths);
        dest.writeDouble(prix);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public List<String> getPhotoPaths() {
        return photoPaths;
    }

    public void setPhotoPaths(List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}


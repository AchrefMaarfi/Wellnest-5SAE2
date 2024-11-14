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

import lombok.Data;

@Data
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
    private List<String> photoPaths; // List to store photo paths
    private double latitude;  // new field for latitude
    private double longitude; // new field for longitude
    @Embedded
    private User user;

    // Parcelable implementation
    // Parcelable implementation
    protected Event(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        adresse = in.readString();
        capacity = in.readInt();
        // Read Date as long
        startDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
        photoPaths = in.createStringArrayList();
        prix = in.readDouble();
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
        dest.writeLong(startDate.getTime()); // Write date as long
        dest.writeLong(endDate.getTime());
        dest.writeStringList(photoPaths);
        dest.writeDouble(prix);
    }

    public Event() {
    }

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
}
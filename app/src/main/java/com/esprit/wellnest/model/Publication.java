package com.esprit.wellnest.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "publications")
public class Publication implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String nompublication ;
    private String adresse ;
    private String description ;
    private int prix ;
    private Date  datepublication ;
    private List<String> pics;
    private float rating;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNompublication() {
        return nompublication;
    }

    public void setNompublication(String nompublication) {
        this.nompublication = nompublication;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Date getDatepublication() {
        return datepublication;
    }

    public void setDatepublication(Date datepublication) {
        this.datepublication = datepublication;
    }

    public Publication() {

    }


    public Publication(int id, String nompublication, String adresse, String description, int prix, Date  datepublication , List<String> pic) {
        this.id = id;
        this.nompublication = nompublication;
        this.adresse = adresse;
        this.description = description;
        this.prix = prix;
        this.datepublication = datepublication;
        this.pics = pic ;
    }

    public Publication(int id, String nompublication, String adresse, String description, int prix, Date datepublication, List<String> pics, int userId) {
        this.id = id;
        this.nompublication = nompublication;
        this.adresse = adresse;
        this.description = description;
        this.prix = prix;
        this.datepublication = datepublication;
        this.pics = pics;
        this.rating = rating;
        this.userId = userId;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", foodItem='" + nompublication + '\'' +
                ", adresse='" + adresse + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", datepublication=" + datepublication +
                ", pic='" + pics + '\'' +
                '}';
    }
}

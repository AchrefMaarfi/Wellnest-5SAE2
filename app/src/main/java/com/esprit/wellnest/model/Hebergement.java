package com.esprit.wellnest.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Hebergement")
@TypeConverters({Converters.class})  // Inclure les converters ici
public class Hebergement {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") // Define column name for ID
    private int id;
    @ColumnInfo(name = "id_User") // Define column name for ID
    private int id_User;

    @ColumnInfo(name = "nom_propriete")
    private String nom_propriete;

    @ColumnInfo(name = "telephone")
    private String telephone;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "Prix")
    private String prix;

    @ColumnInfo(name = "etat")
    private String etat;

    @ColumnInfo(name = "localisation")
    private String localisation;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "date_creation")
    private Date date_creation;

    @ColumnInfo(name = "map")
    private String map;

    public int getId() {
        return id;
    }

    public Hebergement(String nom_propriete, String telephone, String description, String prix, String localisation, String image, Date date_creation) {
        this.nom_propriete = nom_propriete;
        this.telephone = telephone;
        this.description = description;
        this.prix = prix;
        this.localisation = localisation;
        this.image = image;
        this.date_creation = date_creation;
    }

    public Hebergement(String nom_propriete, String telephone, String description, String prix, String localisation, String image, Date date_creation, String map) {
        this.nom_propriete = nom_propriete;
        this.telephone = telephone;
        this.description = description;
        this.prix = prix;
        this.localisation = localisation;
        this.image = image;
        this.date_creation = date_creation;
        this.map = map;
    }

    public Hebergement(String nom_propriete, String telephone, String description, String prix, String etat, String localisation, String image, Date date_creation, String map) {
        this.nom_propriete = nom_propriete;
        this.telephone = telephone;
        this.description = description;
        this.prix = prix;
        this.etat = etat;
        this.localisation = localisation;
        this.image = image;
        this.date_creation = date_creation;
        this.map = map;
    }

    public Hebergement() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_propriete() {
        return nom_propriete;
    }

    public void setNom_propriete(String nom_propriete) {
        this.nom_propriete = nom_propriete;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public int getId_User() {
        return id_User;
    }

    public void setId_User(int id_User) {
        this.id_User = id_User;
    }
}
package com.esprit.wellnest.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;

    private String phone;

    // Champs pour la réinitialisation de mot de passe
    private String resetToken;
    private Date tokenExpirationDate;

    // Constructeur par défaut requis par Room
    public User() {
    }

    // Constructeur personnalisé, annoté avec @Ignore
    @Ignore
    public User(@NonNull String username, @NonNull String password, @NonNull String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getTokenExpirationDate() {
        return tokenExpirationDate;
    }

    public void setTokenExpirationDate(Date tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
    }
}
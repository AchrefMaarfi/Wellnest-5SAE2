package com.esprit.wellnest.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Data
@Entity
public class Pharmacie {
    @ColumnInfo(name = "pharmacie_id")
    @PrimaryKey(autoGenerate = true)
    private int id;
}
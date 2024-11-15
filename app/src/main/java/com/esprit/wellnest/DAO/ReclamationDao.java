package com.esprit.wellnest.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.esprit.wellnest.model.Reclamation;

import java.util.List;

@Dao
public interface ReclamationDao {

    @Insert
    void insert(Reclamation reclamation);

    @Update
    void update(Reclamation reclamation);

    @Delete
    void delete(Reclamation reclamation);

    @Query("SELECT * FROM reclamation WHERE reclamation_id = :id")
    Reclamation getReclamationById(int id);

    @Query("SELECT * FROM reclamation")
    List<Reclamation> getAllReclamations();

    @Query("SELECT * FROM reclamation WHERE email = :email")
    List<Reclamation> getReclamationsByUser(String email);
}
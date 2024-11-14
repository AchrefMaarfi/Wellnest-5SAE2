package com.esprit.wellnest.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.esprit.wellnest.model.Publication;
import com.esprit.wellnest.model.PublicationWithComments;

import java.util.List;

@Dao
public interface PublicationDao {
    @Insert
    void insertPublication(Publication publication);

    @Update
    void update(Publication publication);
    @Delete
    void delete(Publication publication);
    @Query("SELECT * FROM publications")
    List<Publication> getAllPublications();

    // Nouvelle méthode pour obtenir une publication avec ses commentaires
    @Transaction
    @Query("SELECT * FROM publications WHERE id = :publicationId")
    PublicationWithComments getPublicationWithComments(int publicationId);

    @Query("SELECT * FROM publications WHERE id = :publicationId")
    Publication getPublicationById(int publicationId);

    @Query("SELECT * FROM publications WHERE userId = :userId")
    List<Publication> getPublicationsByUserId(int userId);

}

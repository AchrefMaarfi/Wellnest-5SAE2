    package com.esprit.wellnest.DAO;

    import androidx.room.Dao;
    import androidx.room.Delete;
    import androidx.room.Insert;
    import androidx.room.Query;
    import androidx.room.Update;

    import com.esprit.wellnest.model.Hebergement;

    import java.util.List;

    @Dao
    public interface HebergementDao {

        @Insert
        void insertHebergement(Hebergement hebergement);

        @Query("SELECT * FROM Hebergement")
        List<Hebergement>getAllHebergements();

        @Query("SELECT * FROM Hebergement WHERE id = :hebergementId")
        Hebergement getHebergementById(int hebergementId);

        @Delete
        void deleteHebergement(Hebergement hebergement);

        @Update
        void updateHebergement(Hebergement hebergement);

        @Query("DELETE FROM Hebergement")
        void deleteAllHebergements();
        @Query("SELECT * FROM Hebergement h INNER JOIN ReservationHebrgement r ON h.id = r.hebergementID WHERE r.userID = :userID")
        List<Hebergement> getAllHebergementsByUsers(int userID);
        @Query("SELECT * FROM Hebergement  WHERE nom_propriete = :nom")
        List<Hebergement>getAllHebergementsbyNom(String nom);
        @Query("SELECT * FROM Hebergement  WHERE id_User = :id")
        List<Hebergement>getAllHebergementsbyUserId(int id);


    }


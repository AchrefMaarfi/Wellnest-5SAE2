    package com.esprit.wellnest.DAO;

    import androidx.room.Dao;
    import androidx.room.Delete;
    import androidx.room.Insert;
    import androidx.room.Query;
    import androidx.room.Update;

    import com.esprit.wellnest.model.ReservationHebrgement;

    import java.util.List;

    @Dao
    public interface ReservationHebergemntDao {

        @Insert
        void insertReservationHebrgement(ReservationHebrgement reservationHebrgement);

        @Query("SELECT * FROM ReservationHebrgement")
        List<ReservationHebrgement> getAllReservationHebrgements();

        @Query("SELECT * FROM ReservationHebrgement WHERE id = :hebrgementId")
        ReservationHebrgement getReservationHebrgementById(int hebrgementId);
        @Query("SELECT hebergementID FROM ReservationHebrgement WHERE id = :reservation_id")
        int getHebrgementByIdReservation(int reservation_id);

        @Delete
        void deleteReservationHebrgement(ReservationHebrgement reservationHebrgement);

        @Update
        void updateReservationHebrgement(ReservationHebrgement reservationHebrgement);

        @Query("DELETE FROM ReservationHebrgement")
        void deleteAllReservationHebrgement();
        @Query("DELETE  FROM ReservationHebrgement Where id = :hebrgementId")
        void deleteReservationHebrgementById(int hebrgementId);
        @Query("DELETE  FROM ReservationHebrgement Where userID = :idUser AND hebergementID = :hebrgementId ")
        void deleteReservationByIdUserAndIdHotel(int idUser ,int hebrgementId);

        @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM ReservationHebrgement WHERE hebergementID = :hebrgementId AND userId = :idUser")
        boolean ifReservedByThisOne(int hebrgementId, int idUser);









    }


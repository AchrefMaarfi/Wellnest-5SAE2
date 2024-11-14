package com.esprit.wellnest.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.esprit.wellnest.model.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insertReservation(Reservation reservation);

    @Query("SELECT * FROM Reservation")
    List<Reservation> getAllReservations();

    @Query("SELECT * FROM Reservation WHERE id = :reservationId")
    Reservation getReservationById(int reservationId);

    @Delete
    void deleteReservation(Reservation reservation);

    @Update
    void updateReservation(Reservation reservation);

    @Query("DELETE FROM Reservation")
    void deleteAllReservation();
    @Query("DELETE  FROM Reservation Where id = :idReservation")
    void deleteReservationById(int idReservation);
    @Query("DELETE  FROM Reservation Where userID = :idUser AND hotelID = :idHotel ")
    void deleteReservationByIdUserAndIdHotel(int idUser ,int idHotel);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM Reservation WHERE hotelId = :idHotel AND userId = :idUser")
    boolean ifReservedByThisOne(int idHotel, int idUser);
    @Query("SELECT hotelID FROM Reservation WHERE id= :idResrvation")
    int idHotel(int idResrvation);


}


package com.esprit.wellnest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.esprit.wellnest.DAO.EventDAO;
import com.esprit.wellnest.DAO.ForumDAO;
import com.esprit.wellnest.DAO.PharmacieDAO;
import com.esprit.wellnest.DAO.ReclamationDAO;
import com.esprit.wellnest.DAO.ReservationDAO;
import com.esprit.wellnest.DAO.UserDAO;
import com.esprit.wellnest.model.Event;
import com.esprit.wellnest.model.Forum;
import com.esprit.wellnest.model.Pharmacie;
import com.esprit.wellnest.model.Reclamation;
import com.esprit.wellnest.model.Reservation;
import com.esprit.wellnest.model.User;
import com.esprit.wellnest.service.Converters;


@Database(entities = {User.class, Event.class, Pharmacie.class, Reclamation.class, Reservation.class, Forum.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    // Abstract methods to access the DAOs
    public abstract UserDAO userDAO();
    public abstract EventDAO eventDAO();
    public abstract ReservationDAO reservationDAO();
    public abstract ReclamationDAO reclamationDAO();
    public abstract PharmacieDAO pharmacieDAO();
    public abstract ForumDAO forumDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "reunitedD")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration() // Handle migrations here
                    .build();
        }
        return instance;
    }
}
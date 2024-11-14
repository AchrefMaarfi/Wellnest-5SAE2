package com.esprit.wellnest.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.esprit.wellnest.DAO.CommentDao;
import com.esprit.wellnest.DAO.EventDao;
import com.esprit.wellnest.DAO.ForumDao;
import com.esprit.wellnest.DAO.PharmacieDao;
import com.esprit.wellnest.DAO.PublicationDao;
import com.esprit.wellnest.DAO.ReclamationDao;
import com.esprit.wellnest.DAO.ReservationDao;
import com.esprit.wellnest.DAO.UserDao;
import com.esprit.wellnest.model.Comment;
import com.esprit.wellnest.model.Event;
import com.esprit.wellnest.model.Forum;
import com.esprit.wellnest.model.Pharmacie;
import com.esprit.wellnest.model.Reclamation;
import com.esprit.wellnest.model.Publication;
import com.esprit.wellnest.model.Reservation;
import com.esprit.wellnest.model.User;
import com.esprit.wellnest.service.Converters;


@Database(entities = {User.class, Event.class, Publication.class, Comment.class, Pharmacie.class, Reclamation.class, Reservation.class, Forum.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    // Abstract methods to access the DAOs
    public abstract UserDao userDao();
    public abstract EventDao eventDao();
    public abstract ReservationDao reservationDao();
    public abstract ReclamationDao reclamationDao();
    public abstract PharmacieDao pharmacieDao();
    public abstract PublicationDao publicationDao();
    public abstract ForumDao forumDao();
    public abstract CommentDao commentDao();


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
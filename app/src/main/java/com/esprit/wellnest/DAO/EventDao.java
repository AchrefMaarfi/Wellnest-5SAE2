package com.esprit.wellnest.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.esprit.wellnest.model.Event;

import java.util.List;
@Dao
public interface EventDao {
    @Insert
    void insertOne(Event event);
    @Delete
    void delete(Event user);
    @Query("SELECT * FROM events")
    List<Event> getAll();
    @Query("SELECT * FROM events")
    LiveData<List<Event>> getAllEventsLiveData();
    @Query("SELECT * FROM events  e WHERE e.id=:id")
    Event getOneByid(int id);
    @Update
        // Annotation to indicate this method updates an existing event
    void update(Event event);
}
package com.esprit.wellnest.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.esprit.wellnest.model.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert
    void insert(Comment comment);

    @Query("SELECT * FROM comments WHERE publicationId = :publicationId")
    List<Comment> getCommentsForPublication(int publicationId);
}

package com.esprit.wellnest.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comments")
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int publicationId;
    private String content;
    private int userId;

    public Comment(int publicationId, int userId, String content) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPublicationId() { return publicationId; }
    public void setPublicationId(int publicationId) { this.publicationId = publicationId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
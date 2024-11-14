package com.esprit.wellnest.model;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PublicationWithComments {
    @Embedded
    public Publication publication;

    @Relation(parentColumn = "id", entityColumn = "publicationId")
    public List<Comment> comments;
}
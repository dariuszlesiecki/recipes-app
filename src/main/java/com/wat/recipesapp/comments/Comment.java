package com.wat.recipesapp.comments;

import com.wat.recipesapp.user.AuthProvider;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private int rating;
    private long recipeId;

    public Comment(String content, int rating, long recipeId) {
        this.content = content;
        this.rating = rating;
        this.recipeId = recipeId;
    }

    public Comment() {
    }

}

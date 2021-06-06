package com.wat.recipesapp.comments;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private long recipeId;
    private Long userId;
    private String author;
    @Temporal(TemporalType.TIMESTAMP)
    private final Date created = new Date(System.currentTimeMillis());

    public Comment(String content, long recipeId) {
        this.content = content;
        this.recipeId = recipeId;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", recipeId=" + recipeId +
                ", userId=" + userId +
                ", author='" + author + '\'' +
                ", created=" + created +
                '}';
    }


}

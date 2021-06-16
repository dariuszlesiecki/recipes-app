package com.wat.recipesapp.recipies;

import javax.persistence.*;
import java.util.Arrays;

@Entity(name = "Recipies")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private String author;



    @Lob
    private byte[] pic;

    public Recipe(String title, String desciption, Long userId, String author, byte[] pic) {
        this.title = title;
        this.description = desciption;
        this.userId = userId;
        this.author = author;
        this.pic = pic;
    }

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public byte[] getPic() { return pic; }

    public void setPic(byte[] pic) { this.pic = pic; }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", author='" + author + '\'' +
                ", pic=" + Arrays.toString(pic) +
                '}';
    }
}

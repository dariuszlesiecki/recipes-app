package com.wat.recipesapp.externalAPI.rating;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="ratingAPI")
public class RatingAPI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userID;
    private Long recipeID;
    private Double score;

    public RatingAPI() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(Long recipeID) {
        this.recipeID = recipeID;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RatingAPI{" +
                "id=" + id +
                ", userID=" + userID +
                ", recipeID=" + recipeID +
                ", score=" + score +
                '}';
    }
}

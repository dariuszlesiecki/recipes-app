package com.wat.recipesapp.rating;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Optional<Rating> findRatingByUserIDAndRecipeID(long userId, long recipeID){
        return ratingRepository.findRatingByUserIDAndRecipeID(userId,recipeID);
    }

    public double getAverage(Long recipeId){
        List<Rating> ratings = ratingRepository.findAllByRecipeID(recipeId);
        if(ratings.isEmpty()) return 0;
        else return ratings.stream().mapToDouble(Rating::getScore).sum()/ratings.size();
    }

    public void save(Rating rating){
        ratingRepository.save(rating);
    }
}

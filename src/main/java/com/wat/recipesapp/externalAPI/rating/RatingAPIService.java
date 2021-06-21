package com.wat.recipesapp.externalAPI.rating;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingAPIService {
    private final RatingAPIRepository ratingAPIRepository;

    public RatingAPIService(RatingAPIRepository ratingAPIRepository) {
        this.ratingAPIRepository = ratingAPIRepository;
    }

    public Optional<RatingAPI> findRatingByUserIDAndRecipeID(long userId, long recipeID){
        return ratingAPIRepository.findRatingByUserIDAndRecipeID(userId,recipeID);
    }

    public double getAverage(Long recipeId){
        List<RatingAPI> ratings = ratingAPIRepository.findAllByRecipeID(recipeId);
        if(ratings.isEmpty()) return 0;
        else return ratings.stream().mapToDouble(RatingAPI::getScore).sum()/ratings.size();
    }

    public void save(RatingAPI rating){
        ratingAPIRepository.save(rating);
    }
}

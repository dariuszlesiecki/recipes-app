package com.wat.recipesapp.externalAPI.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingAPIRepository extends CrudRepository<RatingAPI,Long> {
    List<RatingAPI> findAllByRecipeID(long recipeID);
    Optional<RatingAPI> findRatingByUserIDAndRecipeID(long userId, long recipeID);
}

package com.wat.recipesapp.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating,Long> {
    List<Rating> findAllByRecipeID(long recipeID);
    Optional<Rating> findRatingByUserIDAndRecipeID(long userId, long recipeID);
}

package com.wat.recipesapp.rating;

import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @RequestMapping("/rating/{recipeId}/add")
    public String add(@PathVariable(name = "recipeId") long recipeId,
                      Rating rating,
                      Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        Rating ratingFromDb = ratingService.findRatingByUserIDAndRecipeID(currentUser.getId(), recipeId).orElse(null);
        if(ratingFromDb == null) {
            rating.setUserID(currentUser.getId());
            rating.setRecipeID(recipeId);
            ratingService.save(rating);
        }
        else{
            ratingFromDb.setScore(rating.getScore());
            ratingService.save(ratingFromDb);
        }

        return "redirect:/recipe/"+recipeId;
    }

}

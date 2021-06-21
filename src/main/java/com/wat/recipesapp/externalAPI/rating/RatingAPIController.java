package com.wat.recipesapp.externalAPI.rating;

import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingAPIController {

    private final RatingAPIService ratingAPIService;
    private final UserService userService;

    public RatingAPIController(RatingAPIService ratingAPIService, UserService userService) {
        this.ratingAPIService = ratingAPIService;
        this.userService = userService;
    }

    @RequestMapping("/ratingAPI/{recipeId}/add")
    public String add(@PathVariable(name = "recipeId") long recipeId,
                      RatingAPI rating,
                      Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        RatingAPI ratingFromDb = ratingAPIService.findRatingByUserIDAndRecipeID(currentUser.getId(), recipeId).orElse(null);
        if(ratingFromDb == null) {
            rating.setUserID(currentUser.getId());
            rating.setRecipeID(recipeId);
            ratingAPIService.save(rating);
        }
        else{
            ratingFromDb.setScore(rating.getScore());
            ratingAPIService.save(ratingFromDb);
        }

        return "redirect:/recipeAPI/"+recipeId;
    }

}

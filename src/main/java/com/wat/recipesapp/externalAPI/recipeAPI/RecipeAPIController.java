package com.wat.recipesapp.externalAPI.recipeAPI;

import com.wat.recipesapp.externalAPI.commentAPI.CommentAPI;
import com.wat.recipesapp.externalAPI.commentAPI.CommentAPIService;
import com.wat.recipesapp.externalAPI.rating.RatingAPI;
import com.wat.recipesapp.externalAPI.rating.RatingAPIService;
import com.wat.recipesapp.image.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Controller
public class RecipeAPIController {

    private final CommentAPIService commentAPIService;
    private final RatingAPIService ratingAPIService;

    public RecipeAPIController(CommentAPIService commentAPIService, RatingAPIService ratingAPIService) {
        this.commentAPIService = commentAPIService;
        this.ratingAPIService = ratingAPIService;
    }

    @RequestMapping("/recipeAPI/{id}")
    public String recipeDetails(Model model,
                                @PathVariable(name = "id") Long id,
                                @ModelAttribute CommentAPI comment,
                                @ModelAttribute RatingAPI ratingAPI){

        String apikey = "apiKey=12a97903387e44c79de8e2e4495e7c82&includeNutrition=true";
        String url = "https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false&"+apikey;
        RestTemplate restTemplate = new RestTemplate();
        RecipeAPI recipe = restTemplate.getForObject(url,RecipeAPI.class);
        model.addAttribute("recipe",recipe);

        List<CommentAPI> comments = commentAPIService.findAllByRecipeId(id);
        comments.sort(Comparator.comparing(CommentAPI::getCreated).reversed());

        model.addAttribute("comments",comments);
        model.addAttribute("comment",comment);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("rating", ratingAPI);
        model.addAttribute("ratingService", ratingAPIService);
        return "recipeAPI";
    }
}

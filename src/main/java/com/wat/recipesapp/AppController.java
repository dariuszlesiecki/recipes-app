package com.wat.recipesapp;

import com.wat.recipesapp.externalAPI.recipeAPI.RecipeAPI;
import com.wat.recipesapp.image.ImageUtil;
import com.wat.recipesapp.recipies.Recipe;
import com.wat.recipesapp.recipies.RecipeService;
import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AppController {

    private final RecipeService recipeService;
    private final UserService userService;

    public AppController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    static class RandomRecipesAPI{
        private RecipeAPI[] recipes;

        public RecipeAPI[] getRecipes() {
            return recipes;
        }

        public void setRecipes(RecipeAPI[] recipes) {
            this.recipes = recipes;
        }
    }

    @GetMapping("/")
    public String homePage(Model model){
        List<Recipe> listRecipes = recipeService.findAll();
        model.addAttribute("listRecipes", listRecipes);
        model.addAttribute("imgUtil", new ImageUtil());



        String apikey = "apiKey=12a97903387e44c79de8e2e4495e7c82&includeNutrition=true";
        String url = "https://api.spoonacular.com/recipes/random?number=10&"+apikey;
        RestTemplate restTemplate = new RestTemplate();
        RandomRecipesAPI listRecipesAPI = restTemplate.getForObject(url,RandomRecipesAPI.class);
        model.addAttribute("listRecipesAPI", Arrays.asList(listRecipesAPI.getRecipes()));

        return "index";
    }

    @RequestMapping("/profile")
    public String myRecipes(Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        Long id = currentUser.getId();
        return "redirect:/user/"+id;
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<String> search(@RequestParam(value="term", required = false) String term){
        List<Recipe> recipes = recipeService.findAllByTitle(term);
        List<String> result = recipes.stream().map(Recipe::getTitle).collect(Collectors.toList());
//TO DO: LINKS!!
        String apikey = "apiKey=12a97903387e44c79de8e2e4495e7c82&includeNutrition=true";
        String url = "https://api.spoonacular.com/recipes/autocomplete?number=6&query="+term+"&"+apikey;
        RestTemplate restTemplate = new RestTemplate();
        RecipeAPI[] resultAPI = restTemplate.getForObject(url,RecipeAPI[].class);
        result.addAll(Arrays.asList(resultAPI).stream().map(e->e.getTitle()).collect(Collectors.toList()));
        return result;
    }

}

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

import java.util.ArrayList;
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

    static class RecipeAutocomplete{
        private String value;
        private String label;

        RecipeAutocomplete(String title, String id, boolean isExternal){
            setLabel(title);
            if(isExternal){
                setValue("/recipeAPI/"+id);
            }
            else{
                setValue("/recipe/"+id);
            }
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "RecipeAutocomplete{" +
                    "value='" + value + '\'' +
                    ", label='" + label + '\'' +
                    '}';
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
    public List<RecipeAutocomplete> search(@RequestParam(value="term", required = false) String term){
        List<Recipe> recipes = recipeService.findAllByTitle(term);
        List<String> result = recipes.stream().map(Recipe::getTitle).collect(Collectors.toList());

        String apikey = "apiKey=12a97903387e44c79de8e2e4495e7c82&includeNutrition=true";
        String url = "https://api.spoonacular.com/recipes/autocomplete?number=6&query="+term+"&"+apikey;
        RestTemplate restTemplate = new RestTemplate();
        RecipeAPI[] recipesAPI = restTemplate.getForObject(url,RecipeAPI[].class);

        List<RecipeAutocomplete> results = new ArrayList<>();
        if(recipes.size() >0 ) results.addAll(recipes.stream().map(e->new RecipeAutocomplete(e.getTitle(),e.getId().toString(),false)).collect(Collectors.toList()));
        if(recipesAPI.length >0) results.addAll(Arrays.stream(recipesAPI).map(e->new RecipeAutocomplete(e.getTitle(),e.getId().toString(),true)).collect(Collectors.toList()));
        System.out.println(results);

        return results;
    }

}

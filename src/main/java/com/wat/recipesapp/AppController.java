package com.wat.recipesapp;

import com.wat.recipesapp.recipies.Recipe;
import com.wat.recipesapp.recipies.RecipeService;
import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppController {

    private final RecipeService recipeService;
    private final UserService userService;

    public AppController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(Model model){
        List<Recipe> listRecipes = recipeService.findAll();
        model.addAttribute("listRecipes", listRecipes);
        return "index";
    }

    @RequestMapping("/profile")
    public String myRecipes(Model model, Authentication authentication){
        List<Recipe> listRecipes = recipeService.findAllByAuthor(authentication.getName());
        model.addAttribute("listRecipes", listRecipes);
        return "recipe_user";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

}

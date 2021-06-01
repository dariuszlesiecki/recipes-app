package com.wat.recipesapp.recipies;

import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/recipe/add")
    public String addRecipe(@ModelAttribute Recipe recipe, Model model){
        model.addAttribute("recipe",recipe);
        return "recipe_add";
    }

    @PostMapping("/recipe/add")
    public String saveRecipe(@Valid Recipe recipe, Authentication authentication) {
        User currentUser = userService.findByEmail(authentication.getName());
        recipe.setUserId(currentUser.getId());
        recipeService.save(recipe);

        return "redirect:/";
    }

    /*
    @RequestMapping("/list")
    public String viewHomePage(Model model) {
        List<Recipe> listProducts = recipeService.findAll();
        model.addAttribute("listProducts", listProducts);

        return "products";
    }
*/




}

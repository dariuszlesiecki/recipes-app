package com.wat.recipesapp.recipies;

import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        recipe.setAuthor(currentUser.getEmail());
        recipeService.save(recipe);

        return "redirect:/";
    }

    @RequestMapping("/recipe/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id){
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("recipe",recipe);
        return "recipe";
    }

}

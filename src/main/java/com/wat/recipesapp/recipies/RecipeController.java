package com.wat.recipesapp.recipies;

import com.wat.recipesapp.comments.Comment;
import com.wat.recipesapp.comments.CommentService;
import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final CommentService commentService;

    public RecipeController(RecipeService recipeService, UserService userService, CommentService commentService) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.commentService = commentService;
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

    @GetMapping("/recipe/{id}/edit")
    public String editRecipe(Model model, @PathVariable(name = "id") Long id, Authentication authentication){
        Long currentUserId = userService.findByEmail(authentication.getName()).getId();
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        if(!currentUserId.equals(recipe.getUserId())){ return "redirect:/"; }

        model.addAttribute("recipe", recipe);
        return "recipe_edit";
    }

    @PostMapping("/recipe/{id}/edit")
    public String saveEditRecipe(@Valid Recipe recipe, @PathVariable(name = "id") Long id) {

        Recipe recipeCopy = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        recipeCopy.setTitle(recipe.getTitle());
        recipeCopy.setDescription(recipe.getDescription());
        recipeService.save(recipeCopy);

        return "redirect:/profile";
    }

    @RequestMapping("/recipe/{id}")
    public String recipeDetails(Model model, @PathVariable(name = "id") Long id, @ModelAttribute Comment comment){
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("recipe",recipe);

        List<Comment> comments = commentService.findAllByRecipeId(id);
        model.addAttribute("comments",comments);
        model.addAttribute("comment",comment);
        return "recipe";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable(name = "id") Long id, Authentication authentication){
        Long currentUserId = userService.findByEmail(authentication.getName()).getId();
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        if(currentUserId.equals(recipe.getUserId())){ recipeService.delete(id); }
        return "redirect:/profile";
    }

}

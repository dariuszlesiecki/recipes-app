package com.wat.recipesapp.recipies;

import com.wat.recipesapp.comments.Comment;
import com.wat.recipesapp.comments.CommentService;
import com.wat.recipesapp.image.ImageUtil;
import com.wat.recipesapp.rating.Rating;
import com.wat.recipesapp.rating.RatingService;
import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;
    private final CommentService commentService;
    private final RatingService ratingService;

    public RecipeController(RecipeService recipeService, UserService userService, CommentService commentService, RatingService ratingService) {
        this.recipeService = recipeService;
        this.userService = userService;
        this.commentService = commentService;
        this.ratingService = ratingService;
    }

    @GetMapping("/recipe/add")
    public String addRecipe(@ModelAttribute Recipe recipe,
                            Model model){
        model.addAttribute("recipe",recipe);
        return "recipe_add";
    }

    @PostMapping("/recipe/add")
    public String saveRecipe(@Valid Recipe recipe,
                             @RequestParam(value = "image", required = false) MultipartFile image,
                             Authentication authentication) throws IOException {
        // Image
        System.out.println("/save | File Name : "+image.getName());
        byte[] imageBytes = image.getBytes();
        recipe.setPic(imageBytes);

        User currentUser = userService.findByEmail(authentication.getName());
        recipe.setUserId(currentUser.getId());
        recipe.setAuthor(currentUser.getEmail());
        recipeService.save(recipe);

        return "redirect:/";
    }

    @GetMapping("/recipe/{id}/edit")
    public String editRecipe(Model model,
                             @PathVariable(name = "id") Long id,
                             Authentication authentication){
        Long currentUserId = userService.findByEmail(authentication.getName()).getId();
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        if(!currentUserId.equals(recipe.getUserId())){ return "redirect:/"; }

        model.addAttribute("recipe", recipe);
        return "recipe_edit";
    }

    @PostMapping("/recipe/{id}/edit")
    public String saveEditRecipe(@Valid Recipe recipe,
                                 @PathVariable(name = "id") Long id,
                                 @RequestParam(value = "image", required = false) MultipartFile image) throws IOException{

        Recipe recipeCopy = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        recipeCopy.setTitle(recipe.getTitle());
        recipeCopy.setDescription(recipe.getDescription());

        // Image
        //System.out.println("/save | File Name : "+image.getName());
        byte[] imageBytes = image.getBytes();
        recipeCopy.setPic(imageBytes);

        recipeService.save(recipeCopy);

        return "redirect:/profile";
    }

    @RequestMapping("/recipe/{id}")
    public String recipeDetails(Model model,
                                @PathVariable(name = "id") Long id,
                                @ModelAttribute Comment comment,
                                @ModelAttribute Rating rating){
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("recipe",recipe);

        List<Comment> comments = commentService.findAllByRecipeId(id);
        comments.sort(Comparator.comparing(Comment::getCreated).reversed());

        model.addAttribute("comments",comments);
        model.addAttribute("comment",comment);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("rating", rating);
        model.addAttribute("ratingService", ratingService);
        return "recipe";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable(name = "id") Long id,
                               Authentication authentication){
        Long currentUserId = userService.findByEmail(authentication.getName()).getId();
        Recipe recipe = recipeService.findById(id).orElseThrow(IllegalArgumentException::new);
        if(currentUserId.equals(recipe.getUserId())){ recipeService.delete(id); }
        return "redirect:/profile";
    }

}

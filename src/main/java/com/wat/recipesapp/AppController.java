package com.wat.recipesapp;

import com.wat.recipesapp.image.ImageUtil;
import com.wat.recipesapp.recipies.Recipe;
import com.wat.recipesapp.recipies.RecipeService;
import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        model.addAttribute("imgUtil", new ImageUtil());
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

}

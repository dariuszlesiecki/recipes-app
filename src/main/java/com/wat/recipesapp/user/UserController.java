package com.wat.recipesapp.user;

import com.wat.recipesapp.recipies.Recipe;
import com.wat.recipesapp.recipies.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class.getName());

    public UserController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping("/user/register")
    public String register(@ModelAttribute UserDTO userDTO,
                           Model model) {
        model.addAttribute("userDTO", userDTO);
        return "register";
    }

    @PostMapping("/user/register")
    public String save(@Valid UserDTO userDTO,
                       BindingResult bindingResult) {
        if(userService.existsByEmail(userDTO.getEmail())){
            bindingResult.addError(new FieldError("userDTO","email","Email address already in use"));
        }

        if(!userDTO.getPassword().equals(userDTO.getRpassword())){
            bindingResult.addError(new FieldError("userDTO","rpassword","Passwords must be the same"));
        }

        if(bindingResult.hasErrors()){
            return "register";
        }
        //log.info(">> userDTO: {}", userDTO.toString());
        userService.register(userDTO);
        return "redirect:/login";
    }

    @RequestMapping("/user/{id}")
    public String usersRecipes(Model model,
                               @PathVariable(name = "id")Long id,
                               Authentication authentication){
        Long currentUserId = userService.findByEmail(authentication.getName()).getId();
        boolean canEdit = currentUserId.equals(id);

        List<Recipe> listRecipes = recipeService.findAllByUserId(id);
        model.addAttribute("listRecipes", listRecipes);
        model.addAttribute("canEdit", canEdit);
        return "recipe_user";
    }


}

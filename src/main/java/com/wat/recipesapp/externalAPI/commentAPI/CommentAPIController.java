package com.wat.recipesapp.externalAPI.commentAPI;

import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommentAPIController {
    private final CommentAPIService commentService;
    private final UserService userService;

    public CommentAPIController(CommentAPIService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping("/commentAPI/{recipeId}/add")
    public String addComment(@PathVariable(name = "recipeId") Long recipeId,
                             CommentAPI comment,
                             Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        comment.setUserId(currentUser.getId());
        comment.setAuthor(currentUser.getEmail());
        comment.setRecipeId(recipeId);
        commentService.save(comment);
        return "redirect:/recipeAPI/"+recipeId;
    }

}

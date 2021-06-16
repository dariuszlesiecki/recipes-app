package com.wat.recipesapp.comments;

import com.wat.recipesapp.user.User;
import com.wat.recipesapp.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping("/comment/{recipeId}/add")
    public String addComment(@PathVariable(name = "recipeId") Long recipeId,
                             Comment comment,
                             Authentication authentication){
        User currentUser = userService.findByEmail(authentication.getName());
        comment.setUserId(currentUser.getId());
        comment.setAuthor(currentUser.getEmail());
        comment.setRecipeId(recipeId);
        commentService.save(comment);
        return "redirect:/recipe/"+recipeId;
    }

}

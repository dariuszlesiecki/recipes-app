package com.wat.recipesapp.comments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    public CommentController(CommentStorage commentStorage) {
        this.commentStorage = commentStorage;
    }

    private final CommentStorage commentStorage;


    @GetMapping("/{id}")
    public List<Comment> findByRecipeId(@PathVariable long id){
       return commentStorage.findByRecipeId(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createRecipe(@RequestBody Comment request){
        return commentStorage.createOrUpdateComment(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable long id){ commentStorage.removeComment(id);}

}

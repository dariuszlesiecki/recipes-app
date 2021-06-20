package com.wat.recipesapp.recipies;
import com.wat.recipesapp.comments.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CommentService commentService;

    public RecipeService(RecipeRepository recipeRepository, CommentService commentService) {
        this.recipeRepository = recipeRepository;
        this.commentService = commentService;
    }

    public List<Recipe> findAll() { return  recipeRepository.findAll(); }
    public List<Recipe> findAllByTitle(String title) { return recipeRepository.findAllByTitleContaining(title); }
    public List<Recipe> findAllByAuthor(String author) {return recipeRepository.findAllByAuthor(author); }
    public List<Recipe> findAllByUserId(Long id) { return recipeRepository.findAllByUserId(id); }
    public Optional<Recipe> findById(long id) { return recipeRepository.findById(id); }
    public void delete(long id){
        commentService.findAllByRecipeId(id).forEach(e -> commentService.deleteById(e.getId()));
        recipeRepository.deleteById(id);
    }
    public void save(Recipe recipe){recipeRepository.save(recipe);}
}

package com.wat.recipesapp.recipies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    private final RecipeRepository recipeRepository;

    public List<Recipe> findAll() { return  recipeRepository.findAll(); }
    public List<Recipe> findAllByAuthor(String author) {return recipeRepository.findAllByAuthor(author); }
    public List<Recipe> findAllByUserId(Long id) { return recipeRepository.findAllByUserId(id); }
    public Optional<Recipe> findById(long id) { return recipeRepository.findById(id); }
    public void delete(long id){ recipeRepository.deleteById(id);}
    public void save(Recipe recipe){recipeRepository.save(recipe);}
}

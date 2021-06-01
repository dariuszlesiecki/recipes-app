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
    public Optional<Recipe> findById(long id) { return recipeRepository.findById(id); }
    public void removeRecipe(long id){ recipeRepository.deleteById(id);}
    public Recipe createOrUpdateRecipe(Recipe recipe){ return  recipeRepository.save(recipe);}
    public void save(Recipe recipe){recipeRepository.save(recipe);}
}

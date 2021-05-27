package com.wat.recipesapp.recipies;

import com.wat.recipesapp.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipies")
public class RecipeController {
    public RecipeController(RecipeStorage recipeStorage) {
        this.recipeStorage = recipeStorage;
    }

    private final RecipeStorage recipeStorage;

    @GetMapping
    public List<Recipe> findAll(){
        return recipeStorage.findAll();
    }


    @GetMapping("/{id}")
    public Recipe findById(@PathVariable long id){
        final Recipe recipe = recipeStorage.findById(id).orElseThrow(
                () -> new NotFoundException("Recipe does not exist"));
        return recipe;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe createRecipe(@RequestBody Recipe request){
        return recipeStorage.createOrUpdateRecipe(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable long id){ recipeStorage.removeRecipe(id);}



}

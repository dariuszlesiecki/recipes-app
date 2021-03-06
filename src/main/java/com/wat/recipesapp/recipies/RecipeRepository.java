package com.wat.recipesapp.recipies;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAll();
    List<Recipe> findAllByTitleContainingIgnoreCase(String title);
    List<Recipe> findAllByAuthor(String author);
    List<Recipe> findAllByUserId(Long id);
    Optional<Recipe> findById(Long i);
    void deleteById(Long i);
}

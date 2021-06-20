package com.wat.recipesapp.externalAPI.commentAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentAPIRepository extends JpaRepository<CommentAPI, Long> {
    List<CommentAPI> findAllByRecipeId(long id);
    void deleteById(long id);

}
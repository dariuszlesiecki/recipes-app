package com.wat.recipesapp.externalAPI.commentAPI;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentAPIService {
    private final CommentAPIRepository commentRepository;

    public CommentAPIService(CommentAPIRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentAPI> findAllByRecipeId(long id){ return commentRepository.findAllByRecipeId(id);}
    public void save(CommentAPI comment){commentRepository.save(comment);}
    public void deleteById(long id){ commentRepository.deleteById(id);}
}

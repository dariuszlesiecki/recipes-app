package com.wat.recipesapp.comments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentStorage {
    private final CommentRepository commentRepository;

    public CommentStorage(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public List<Comment> findByRecipeId(long id){ return commentRepository.findByRecipeId(id);}
    public Comment createOrUpdateComment(Comment comment){ return commentRepository.save(comment);}
    public void removeComment(long id){ commentRepository.deleteById(id);}
}

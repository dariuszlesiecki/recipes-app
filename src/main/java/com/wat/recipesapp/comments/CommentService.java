package com.wat.recipesapp.comments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAllByRecipeId(long id){ return commentRepository.findAllByRecipeId(id);}
    public void save(Comment comment){commentRepository.save(comment);}
    public void deleteById(long id){ commentRepository.deleteById(id);}
}

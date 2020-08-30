package com.sqh.bloggingapp.services;

import com.sqh.bloggingapp.models.Comment;
import com.sqh.bloggingapp.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment){
        return commentRepository.saveAndFlush(comment);
    }

    public List<Comment> findAllByPostId(int id){
        return commentRepository.findAllByPostIdOrderByCreatedAtAsc(id);
    }
}

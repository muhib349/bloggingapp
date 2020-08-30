package com.sqh.bloggingapp.repositories;

import com.sqh.bloggingapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPostIdOrderByCreatedAtAsc(int post_id);
}

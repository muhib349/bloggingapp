package com.sqh.bloggingapp.repositories;

import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserOrderByCreatedAtDesc(User user);
    List<Post> findAllByIsApprovedTrue();
    //List<Post> findAllOrderByCreatedAtDesc();
}

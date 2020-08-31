package com.sqh.bloggingapp.repositories;

import com.sqh.bloggingapp.models.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DislikeRepository extends JpaRepository<Dislike, Integer> {
    List<Dislike> findAllByPostIdAndUserId(int post_id, int user_id);
}

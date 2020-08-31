package com.sqh.bloggingapp.repositories;

import com.sqh.bloggingapp.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {
    List<Like> findAllByPostIdAndUserId(int post_id, int user_id);
}

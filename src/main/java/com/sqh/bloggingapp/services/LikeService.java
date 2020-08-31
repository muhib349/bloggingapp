package com.sqh.bloggingapp.services;

import com.sqh.bloggingapp.models.Like;
import com.sqh.bloggingapp.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public void save(Like like){
        likeRepository.save(like);
    }

    public List<Like> findAllByPostIdAndUserId(int post_id, int user_id){
        return likeRepository.findAllByPostIdAndUserId(post_id,user_id);
    }
}

package com.sqh.bloggingapp.services;

import com.sqh.bloggingapp.models.Dislike;
import com.sqh.bloggingapp.repositories.DislikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DislikeService {
    private DislikeRepository dislikeRepository;

    @Autowired
    public DislikeService(DislikeRepository dislikeRepository) {
        this.dislikeRepository = dislikeRepository;
    }

    public void save(Dislike dislike){
        dislikeRepository.save(dislike);
    }
    public List<Dislike> findAllByPostIdAndUserId(int post_id, int user_id){
        return dislikeRepository.findAllByPostIdAndUserId(post_id,user_id);
    }
}

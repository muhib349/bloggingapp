package com.sqh.bloggingapp.services;

import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.repositories.PostRepository;
import com.sqh.bloggingapp.repositories.UserRepository;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post save(Post post){
        post.setApproved(false);
        return postRepository.saveAndFlush(post);
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAllPostsByUser(User user) {
       return   postRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void approveBlogPost(Post post){
        post.setApproved(true);
        postRepository.save(post);
    }

    public void deactivateBlogPost(Post post){
        post.setApproved(false);
        postRepository.save(post);
    }

    public List<Post> findAllApprovedPost() {
        return postRepository.findAllByIsApprovedTrue();
    }
}

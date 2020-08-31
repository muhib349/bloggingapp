package com.sqh.bloggingapp.controllers;

import com.sqh.bloggingapp.models.Like;
import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.services.LikeService;
import com.sqh.bloggingapp.services.PostService;
import com.sqh.bloggingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class LikeController {
    private PostService postService;
    private LikeService likeService;
    private UserService userService;

    @Autowired
    public LikeController(PostService postService, LikeService likeService,UserService userService) {
        this.postService = postService;
        this.likeService = likeService;
        this.userService = userService;
    }

    @PostMapping("/saveLike")
    public String saveLike(@RequestParam int post_id, Principal principal){
        Optional<Post> optionalPost = postService.findById(post_id);

        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            Optional<User> optionalUser = userService.findByUsername(principal.getName());

            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                List<Like> likes =  likeService.findAllByPostIdAndUserId(post.getId(),user.getId());
                if(likes != null && likes.size() > 0){
                    return "redirect:/post/"+post.getId();
                }else{
                    Like like = new Like();
                    like.setPost(post);
                    like.setUser(user);
                    likeService.save(like);
                    return "redirect:/post/"+post.getId();
                }
            }else {
                return "error";
            }
        }else{
            return "error";
        }
    }
}

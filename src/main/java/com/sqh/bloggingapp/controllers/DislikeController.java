package com.sqh.bloggingapp.controllers;

import com.sqh.bloggingapp.models.Dislike;
import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.services.DislikeService;
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
public class DislikeController {
    private PostService postService;
    private DislikeService dislikeService;
    private UserService userService;

    @Autowired
    public DislikeController(PostService postService, DislikeService dislikeService, UserService userService) {
        this.postService = postService;
        this.dislikeService = dislikeService;
        this.userService = userService;
    }

    @PostMapping("/saveDislike")
    public String saveDislike(@RequestParam int post_id, Principal principal){
        Optional<Post> optionalPost = postService.findById(post_id);

        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            Optional<User> optionalUser = userService.findByUsername(principal.getName());
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                List<Dislike> dislikes =  dislikeService.findAllByPostIdAndUserId(post.getId(),user.getId());
                if(dislikes != null && dislikes.size() > 0){
                    return "redirect:/post/"+post.getId();
                }else{
                    Dislike dislike = new Dislike();
                    dislike.setPost(post);
                    dislike.setUser(user);
                    dislikeService.save(dislike);
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

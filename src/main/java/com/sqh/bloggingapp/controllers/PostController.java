package com.sqh.bloggingapp.controllers;

import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.services.PostService;
import com.sqh.bloggingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class PostController {

    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/createPost")
    public String createPost(Principal principal, Model model){

        Optional<User> user = userService.findByUsername(principal.getName());

        if(user.isPresent()){
            Post post = new Post();
            post.setUser(user.get());
            model.addAttribute("post",post);
            return "postForm";
        }
        return "error";
    }

    @PostMapping("/savePost")
    public String savePost(@Valid Post post, BindingResult result, Model model){
        if(result.hasErrors()){
            return "postForm";
        }
        postService.save(post);
        List<Post> posts = postService.findAllPostsByUser(post.getUser());
        model.addAttribute("successMessage","Post has been added successfully.Wait for admin approval!");
        model.addAttribute("posts", posts);
        model.addAttribute("user", post.getUser());
        return "posts";
    }

    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable int id,Principal principal, Model model){

        Optional<Post> optionalPost = postService.findById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            if (isPrincipalOwnerOfPost(principal, post)) {
                model.addAttribute("username", principal.getName());
            }
            return "post";
        }else{
            return "error";
        }
    }

    @GetMapping("/editPost/{id}")
    public String editPost(@PathVariable int id,
                                 Principal principal,
                                 Model model) {

        Optional<Post> optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPrincipalOwnerOfPost(principal, post)) {
                model.addAttribute("post", post);
                return "/postForm";
            }
        }
        return "error";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable int id){
        Optional<Post> opPost=  postService.findById(id);

        if(opPost.isPresent()){
            Post post = opPost.get();
            postService.delete(post);
            return "redirect:/blog/"+post.getUser().getUsername();
        }else
            return "error";
    }

    @GetMapping("/blog/{username}")
    public String getUserBlogPost(@PathVariable String username, Model model){
        Optional<User> opUser = userService.findByUsername(username);

        if(opUser.isPresent()){
            List<Post> posts = postService.findAllPostsByUser(opUser.get());
            model.addAttribute("posts",posts);
            model.addAttribute("user",opUser.get());
            return "posts";
        }else
            return "error";
    }
    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }
}

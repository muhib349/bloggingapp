package com.sqh.bloggingapp.controllers;

import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    private PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Post> posts =  postService.findAllApprovedPost();
        model.addAttribute("posts",posts);
        return "home";
    }
}

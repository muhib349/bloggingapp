package com.sqh.bloggingapp.controllers;

import com.sqh.bloggingapp.models.Comment;
import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.services.CommentService;
import com.sqh.bloggingapp.services.PostService;
import com.sqh.bloggingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {

    private PostService postService;
    private UserService userService;
    private CommentService commentService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping("/createComment/{post_id}")
    public String createComment(@PathVariable int post_id, Principal principal, Model model){
        Optional<Post> post = postService.findById(post_id);

        if(post.isPresent()){
            Optional<User> user = userService.findByUsername(principal.getName());
            if(user.isPresent()){
                Comment comment = new Comment();
                comment.setPost(post.get());
                comment.setUser(user.get());
                model.addAttribute("comment", comment);
                return "commentForm";
            }else{
                return "error";
            }
        }else
            return "error";
    }

    @PostMapping("/saveComment")
    public String saveComment(@Valid  Comment comment, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "/commentForm";

        } else {
            commentService.save(comment);
            return "redirect:/post/" + comment.getPost().getId();
        }
    }
}

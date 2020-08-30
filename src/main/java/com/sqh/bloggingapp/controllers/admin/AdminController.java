package com.sqh.bloggingapp.controllers.admin;

import com.sqh.bloggingapp.models.Post;
import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.services.PostService;
import com.sqh.bloggingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private PostService postService;

    @Autowired
    public AdminController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/home")
    public String home(){
        return "admin/home";
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "admin/registration";
    }

    @PostMapping("/registration")
    public String saveUser(@Valid User user, BindingResult result, Model model){
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user",
                    "There is already a user registered with the email provided");
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            result.rejectValue("username", "error.user",
                    "There is already a user registered with the username provided");
        }

        if (result.hasErrors()) {
            return "admin/registration";
        }
        userService.saveAdminUser(user);
        model.addAttribute("successMessage", "Admin has been registered successfully");

        return "admin/home";
    }

    @PostMapping("/approveUser")
    public String approveUser(@RequestParam String user_id){
        Optional<User> optionalUser =  userService.findById(Integer.parseInt(user_id));
        if(optionalUser.isPresent()){
            User user = userService.approveUserAccount(optionalUser.get());
            return "redirect:/admin/users";
        }else{
            return "error";
        }
    }

    @PostMapping("/deactivateUser")
    public String deactivateUser(@RequestParam String user_id){
        Optional<User> optionalUser =  userService.findById(Integer.parseInt(user_id));
        if(optionalUser.isPresent()){
            User user = userService.deActivateUserAccount(optionalUser.get());
            return "redirect:/admin/users";
        }else{
            return "error";
        }
    }

    @GetMapping("/users")
    public String manageUser(Model model){
        List<User> users = userService.findAllByRoles("ROLE_USER");
        model.addAttribute("users",users);
        return "admin/users";
    }

    @GetMapping("/posts")
    public String managePost(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "admin/posts";
    }

    @PostMapping("/approvePost")
    public String approvePost(@RequestParam String post_id, Model model){
        Optional<Post> optionalPost =  postService.findById(Integer.parseInt(post_id));
        if(optionalPost.isPresent()){
            postService.approveBlogPost(optionalPost.get());
            List<Post> posts = postService.findAll();
            model.addAttribute("successMessage", "Post has been approved successfully.");
            model.addAttribute("posts", posts);
            return "admin/posts";
        }else{
            return "error";
        }
    }


    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable int id, Model model){
        Optional<Post> optionalPost = postService.findById(id);

        if(optionalPost.isPresent()){
            postService.delete(optionalPost.get());
            List<Post> posts = postService.findAll();
            model.addAttribute("successMessage", "Post has been deleted successfully.");
            model.addAttribute("posts", posts);
            return "admin/posts";
        }else{
            return "error";
        }
    }
}

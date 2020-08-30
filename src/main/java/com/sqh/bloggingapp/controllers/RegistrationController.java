package com.sqh.bloggingapp.controllers;

import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RegistrationController {


    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
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
            return "registration";
        }
        userService.save(user);
        model.addAttribute("successMessage", "User has been registered successfully! Wait for admin approval");
        //model.addAttribute("user", new User());

        return "login";
    }

}

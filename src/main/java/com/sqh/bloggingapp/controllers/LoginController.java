package com.sqh.bloggingapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Principal principal){
        if (principal != null) {
            return "redirect:/home";
        }
        return "login";
    }
}

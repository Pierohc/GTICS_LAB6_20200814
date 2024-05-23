package com.example.lab6.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping(value="/loginForm")
    public String loginForm(){
        return "login";
    }




}

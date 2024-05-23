package com.example.lab6.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/main", method= RequestMethod.GET)
public class LoginController {


    @GetMapping(value="")
    public String login(){


        return "login";
    }


}

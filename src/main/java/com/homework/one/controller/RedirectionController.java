package com.homework.one.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {

    @GetMapping(value = "/sign-in")
    public String index() {
        return "sign-in";
    }

    @GetMapping(value = "/sign-up")
    public String signup() {
        return "sign-up";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "sign-in?logout";
    }

   // @GetMapping(value = "/")

}

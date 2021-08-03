package br.com.personalportifolio.brewer.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return user != null ? "redirect:/beer" : "/login/login";
    }
}

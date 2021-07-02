package com.practice.controller;

import com.practice.config.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String home(Model model){
        SessionMember member = (SessionMember) httpSession.getAttribute("member");
        System.out.println("member = " + member);
        model.addAttribute("member", member);

        return "home";
    }
}

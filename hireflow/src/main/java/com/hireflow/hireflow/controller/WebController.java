package com.hireflow.hireflow.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Object loggedInUser = session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            com.hireflow.hireflow.entity.User user =
                    (com.hireflow.hireflow.entity.User) loggedInUser;

            model.addAttribute("username", user.getUsername());
            model.addAttribute("role", user.getRole());
        }

        return "dashboard";
    }

    @GetMapping("/jobs")
    public String jobs() {
        return "jobs";
    }

    @GetMapping("/applicants")
    public String applicants() {
        return "applicants";
    }

    @GetMapping("/panel-members")
    public String panelMembers() {
        return "panel-members";
    }

    @GetMapping("/interviews")
    public String interviews() {
        return "interviews";
    }

    @GetMapping("/feedback")
    public String feedback() {
        return "feedback";
    }
}
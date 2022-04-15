package org.citizenGuesser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    @Value("${app.name}")
    private String appName;

    @GetMapping
    public String home(final Model model) {
        model.addAttribute("appName", appName);
        return "index";
    }
}

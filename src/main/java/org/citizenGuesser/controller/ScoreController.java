package org.citizenGuesser.controller;

import org.citizenGuesser.service.GifAssigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/score")
public class ScoreController {
    private int score;
    private String gifUrl;

    @Value("${app.name}")
    private String appName;

    @Autowired
    private GifAssigner gifAssigner;

    @GetMapping
    public String score(final Model model){
        setGif();
        model.addAttribute("gif", gifUrl);
        model.addAttribute("score", score);
        model.addAttribute("appName", appName);
        return "score";
    }

    @PostMapping(value = "/retrieve")
    public String retrieve(@RequestParam int score) {
        this.score = score;
        return "redirect:/score";
    }

    private void setGif() {
        gifUrl = gifAssigner.getGifUrl(score);
    }

}

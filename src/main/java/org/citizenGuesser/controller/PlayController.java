package org.citizenGuesser.controller;

import org.citizenGuesser.model.City;
import org.citizenGuesser.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.citizenGuesser.service.CityAssigner;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/play")
public class PlayController {
    private City knownCity;
    private City unknownCity;
    private int score = 0;
    private HashMap<String, List<String>> pickedCities;
    private boolean hardMode;
    private int hearts;
    private final int hardModeAllowedErrors = 3;

    @Autowired
    private CityAssigner cityAssigner;

    @Autowired
    private DataService dataService;

    @Value("${app.name}")
    private String appName;

    @PostMapping(value = "/start")
    public String start(@RequestParam String mode)
    {
        if (mode.equals("facile")){
            hardMode = false;
            hearts = 0;
        }
        else {
            hardMode = true;
            hearts = hardModeAllowedErrors;
        }
        return "redirect:/play";
    }

    @GetMapping
    public String play(final Model model){
        if(score == 0){
            pickedCities = new HashMap<>();
            initPickedCities();
        }
        setKnownCity();
        setUnknownCity();

        model.addAttribute("knownCity", knownCity);
        model.addAttribute("unknownCity", unknownCity);
        model.addAttribute("score", score);
        model.addAttribute("appName", appName);
        model.addAttribute("hardMode", hardMode);
        model.addAttribute("hearts", hearts);
        model.addAttribute("allowedErrors", hardModeAllowedErrors);
        return "play";
    }

    @PostMapping(value = "/answer")
    public String answer(@RequestParam String answer,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request)
    {
        if((answer.equals("Plus") && knownCity.getPopulation() > unknownCity.getPopulation())
            || (answer.equals("Moins") && knownCity.getPopulation() < unknownCity.getPopulation())) {

            hearts--;
            if(hearts > 0) {
                knownCity = unknownCity;
                return "redirect:/play";
            }

            request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
            redirectAttributes.addAttribute("score", score);
            reset();
            return "redirect:/score/retrieve";
        }
        else {
            score += hardMode ? 2 : 1;
            knownCity = unknownCity;
            return "redirect:/play";
        }
    }

    private void setKnownCity() {
        if(knownCity == null) {
            knownCity = cityAssigner.getRandomCity(pickedCities, hardMode);
            addToPickedCities(knownCity);
        }
    }

    private void setUnknownCity() {
        unknownCity = cityAssigner.getRandomCity(pickedCities, hardMode);
        addToPickedCities(unknownCity);
    }

    private void initPickedCities() {
        List<String> departments = dataService.getDepartments();
        for (String department: departments) {
            pickedCities.put(department, new ArrayList<>());
        }
    }

    private void reset(){
        knownCity = null;
        unknownCity = null;
        score = 0;
    }

    private void addToPickedCities(City city) {
        String department = city.getCodesPostaux()[0].substring(0,2);
        if(department.equals("97")) {
            department = city.getCodesPostaux()[0].substring(0,3);
        }
        if(department.equals("20")){
            department = city.getCode().substring(0, 2);
        }
        pickedCities.get(department).add(city.getCode());
    }
}

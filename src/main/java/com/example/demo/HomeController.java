package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {
    @Autowired
    TwittRepository  twittRepository;

    @RequestMapping("/")
    public String listTwitts(Model model){
        model.addAttribute("twitts",  twittRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String  twittForm(Model model){
        model.addAttribute("twitt", new  Twitt());
        return "twittform";
    }

    @PostMapping("/process")
    public String processForm(@Valid  Twitt  twitt, BindingResult result){
        if (result.hasErrors()){
            return "twittform";
        }
        twittRepository.save(twitt);
        return "redirect:/";
    }

}


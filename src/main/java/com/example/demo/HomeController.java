package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;


@Controller
public class HomeController {
    @Autowired
    TwittRepository  twittRepository;

    @Autowired
    CloudinaryConfig cloudc;

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
/*
    @PostMapping("/process")
    public String processForm(@Valid  Twitt  twitt, BindingResult result){
        if (result.hasErrors()){
            return "twittform";
        }
        twittRepository.save(twitt);
        return "redirect:/";
    }

 */

    @PostMapping("/add")
    public String processTwitt(@ModelAttribute Twitt twitt,
                               @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            twitt.setImage(uploadResult.get("url").toString());
            twittRepository.save(twitt);

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }

}


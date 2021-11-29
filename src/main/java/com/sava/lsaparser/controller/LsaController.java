package com.sava.lsaparser.controller;

import com.sava.lsaparser.dto.Lsa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/lsa")
public class LsaController {
    @GetMapping
    public String showLsaInputForm(Model model) {
        model.addAttribute("lsa", new Lsa());
        return "lsaInput";
    }

    @PostMapping
    public String createLsa(@ModelAttribute Lsa lsa, Model model) {
        model.addAttribute("lsa", lsa);
        return "inputFormResult";
    }
}

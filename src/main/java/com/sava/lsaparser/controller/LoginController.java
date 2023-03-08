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
@RequestMapping("/login")
public class LoginController {

  @GetMapping
  public String showLoginForm(Model model) {
    model.addAttribute("lsa", new Lsa());
    return "login";
  }

  @PostMapping
  public String login(@ModelAttribute("username") String username,
      @ModelAttribute("password") String password, Model model) {
    model.addAttribute("username", username);
    model.addAttribute("password", password);
    return "redirect:/home";
  }
}

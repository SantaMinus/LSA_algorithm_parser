package com.sava.lsaparser.controller;

import com.sava.lsaparser.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@RequestMapping("/user/registration")
@RequiredArgsConstructor
public class RegistrationController {

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }
}

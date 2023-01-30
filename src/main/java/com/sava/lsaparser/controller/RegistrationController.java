package com.sava.lsaparser.controller;

import com.sava.lsaparser.dto.UserDto;
import com.sava.lsaparser.exception.UserAlreadyExistsException;
import com.sava.lsaparser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/user/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
            HttpServletRequest request, Errors errors) {
        try {
            userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistsException e) {
            log.debug("An account for email {} already exists", userDto.getEmail());
        }
        return new ModelAndView("successRegister", "user", userDto);
    }
}

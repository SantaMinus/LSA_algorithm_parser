package com.sava.lsaparser.controller;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.exception.LsaValidationException;
import com.sava.lsaparser.service.LsaValidatorService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/lsa")
@RequiredArgsConstructor
public class LsaController {

  private final LsaValidatorService lsaValidatorService;

  @GetMapping
  public String showLsaInputForm(Model model) {
    model.addAttribute("lsa", new Lsa());
    return "lsaInput";
  }

  @PostMapping
  public String createLsa(@ModelAttribute("lsa") @Valid Lsa lsa, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      return "lsaInput";
    }
    try {
      // TODO: put to functional interface
      lsaValidatorService.validate(lsa.getAlgorithmInput());
    } catch (LsaValidationException e) {
      // TODO: handle exception
    }
    model.addAttribute("lsa", lsa);
    return "inputFormResult";
  }
}

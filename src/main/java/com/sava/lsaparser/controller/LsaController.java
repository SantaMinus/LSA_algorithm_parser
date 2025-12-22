package com.sava.lsaparser.controller;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.parser.LSAParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Controller
@RequestMapping("/lsa")
@RequiredArgsConstructor
public class LsaController {

  private final LSAParser lsaParser;

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
    LSAParser.lsa = new ArrayList<>(Arrays.asList(lsa.getAlgorithmInput().split("\\s* \\s*")));
    lsaParser.calculate();
    model.addAttribute("lsa", lsa);
    return "inputFormResult";
  }
}

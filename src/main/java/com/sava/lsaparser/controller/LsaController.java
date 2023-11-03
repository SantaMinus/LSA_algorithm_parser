package com.sava.lsaparser.controller;

import com.sava.lsaparser.dto.Lsa;
import com.sava.lsaparser.service.LsaProcessingService;
import com.sava.lsaparser.structure.Node;
import java.util.List;
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

  private final LsaProcessingService lsaProcessingService;

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
    List<Node> generatedLsa = lsaProcessingService.processLsa(lsa);
    model.addAttribute("lsa", generatedLsa.toString());
    return "inputFormResult";
  }
}

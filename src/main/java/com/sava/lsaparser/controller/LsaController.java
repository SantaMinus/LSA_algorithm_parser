package com.sava.lsaparser.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/lsa")
public class LsaController {
    @PostMapping
    public void createLsa(@RequestBody String lsaString) {
        log.info("LSA input: {}", lsaString);
    }
}

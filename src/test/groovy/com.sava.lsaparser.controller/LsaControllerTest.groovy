package com.sava.lsaparser.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest
@AutoConfigureMockMvc
class LsaControllerTest extends Specification {
    @Autowired
    MockMvc mockMvc

    def "GET request shows LSA input form"() {
        expect:
        mockMvc.perform(get("/lsa")).andExpect(view().name('lsaInput'))
    }
}
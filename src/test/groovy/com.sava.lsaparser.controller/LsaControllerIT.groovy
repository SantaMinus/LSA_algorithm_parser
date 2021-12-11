package com.sava.lsaparser.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest
@AutoConfigureMockMvc
class LsaControllerIT extends Specification {
    @Autowired
    MockMvc mockMvc

    def "GET request shows LSA input form"() {
        expect:
        mockMvc.perform(get("/lsa")).andExpect(view().name('lsaInput'))
    }

    def "POST creates an LSA"() {
        given:
        def inputString = 'test'

        when:
        def res = mockMvc.perform(post("/lsa")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content('algorithmInput=' + inputString))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        then:
        res.contains('LSA: ' + inputString)
    }

    def "POST validates an LSA for non-blank algorithmInput"() {
        given:
        def inputString = input

        expect:
        mockMvc.perform(post("/lsa")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content('algorithmInput=' + inputString))
                .andExpect(status().isBadRequest())

        where:
        input << ['', '   ']
    }
}
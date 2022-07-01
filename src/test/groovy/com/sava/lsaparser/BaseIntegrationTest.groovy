package com.sava.lsaparser

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
class BaseIntegrationTest extends Specification {
    @Autowired
    MockMvc mockMvc
}

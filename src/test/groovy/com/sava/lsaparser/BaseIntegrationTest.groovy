package com.sava.lsaparser

import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class BaseIntegrationTest extends Specification {
  @Autowired
  MockMvc mockMvc
  @Autowired
  private WebApplicationContext webApplicationContext

  @Before
  void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
  }
}

package com.sava.lsaparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
public class BaseIntegrationNoAuthTest {

  @Autowired
  public MockMvc mockMvc;
}

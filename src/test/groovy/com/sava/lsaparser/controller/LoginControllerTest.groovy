package com.sava.lsaparser.controller

import com.sava.lsaparser.BaseIntegrationTest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class LoginControllerTest extends BaseIntegrationTest {
  def "GET request shows LSA input form"() {
    expect:
    mockMvc.perform(get('/login'))
        .andExpect(status().isOk())
        .andExpect(view().name('login'))
  }

  def "POST logs user in and redirects to /home"() {
    expect:
    mockMvc.perform(post('/login'))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name('redirect:/home'))
  }
}

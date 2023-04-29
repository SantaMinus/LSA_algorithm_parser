package com.sava.lsaparser.controller

import com.sava.lsaparser.BaseIntegrationTest
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@SpringBootTest
@ActiveProfiles("test")
@WithMockUser(roles = "USER")
@RunWith(SpringRunner.class)
class LsaControllerIT extends BaseIntegrationTest {

  def "GET request shows LSA input form"() {
    expect:
    mockMvc.perform(get("/lsa"))
        .andExpect(status().isOk())
        .andExpect(view().name('lsaInput'))
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

    when:
    def res = mockMvc.perform(post("/lsa")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content('algorithmInput=' + inputString))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString()

    then:
    assertThat(res, allOf(
        containsString('validationError'),
        containsString('Please correct the problems below and resubmit')))

    where:
    input << ['', '   ']
  }
}

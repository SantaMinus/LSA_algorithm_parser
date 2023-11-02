package com.sava.lsaparser.controller

import com.sava.lsaparser.BaseIntegrationTest
import com.sava.lsaparser.service.LsaValidatorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WithMockUser(roles = "USER")
class LsaControllerIT extends BaseIntegrationTest {
  @Autowired
  private LsaValidatorService validatorService

  def "GET request shows LSA input form"() {
    expect:
    mockMvc.perform(get("/lsa"))
        .andExpect(status().isOk())
        .andExpect(view().name('lsaInput'))
  }

  def "POST creates an LSA"() {
    given:
    def inputString = 'b y1 e'
    def expectedRes = '''[Node(name=b, id=0, n=0, next=Node(name=y, id=1, n=1, next=Node(name=e, id=0, n=2, next=null))), Node(name=y, id=1, n=1, next=Node(name=e, id=0, n=2, next=null)), Node(name=e, id=0, n=2, next=null)]'''

    when:
    def res = mockMvc.perform(post("/lsa")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content('algorithmInput=' + inputString))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString()

    then:
    res.contains(expectedRes)
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

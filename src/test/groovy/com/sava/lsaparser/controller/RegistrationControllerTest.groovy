package com.sava.lsaparser.controller


import com.sava.lsaparser.BaseIntegrationTest
import com.sava.lsaparser.entity.User
import com.sava.lsaparser.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class RegistrationControllerTest extends BaseIntegrationTest {
  def repo = Mock(UserRepository)
  def passwordEncoder = new BCryptPasswordEncoder()
  def controller = new RegistrationController(repo, passwordEncoder)

  def "GET request shows registration form"() {
    expect:
    mockMvc.perform(get('/register'))
        .andExpect(status().isOk())
        .andExpect(view().name('registration'))
  }

  def "processRegistration() saves a user and redirects to /login"() {
    given:
    def form = new RegistrationForm(username: 'user', password: 'pass', fullname: 'User Name')

    when:
    def res = controller.processRegistration(form)

    then:
    res == 'redirect:/login'
    1 * repo.save(_ as User) >> { User user ->
      user.username == 'user' &&
          user.password == 'pass' &&
          user.fullname == 'User Name'
    }
  }
}

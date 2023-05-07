package com.sava.lsaparser.service

import com.sava.lsaparser.BaseIntegrationTest
import com.sava.lsaparser.entity.User
import com.sava.lsaparser.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserRepositoryUserDetailsServiceTest extends BaseIntegrationTest {
  def repository = Mock(UserRepository)
  UserRepositoryUserDetailsService service = new UserRepositoryUserDetailsService(repository)

  def "loadUserByUsername throws UsernameNotFoundException when user doesn't exist"() {
    given:
    def username = 'nonExistingUser'
    repository.findByUsername(username) >> null

    when:
    service.loadUserByUsername(username)

    then:
    thrown(UsernameNotFoundException)
  }

  def "loadUserByUsername returns a user if found"() {
    given:
    def username = 'existingUser'
    def user = new User(username, 'password', 'full name')
    repository.findByUsername(username) >> user

    when:
    def result = service.loadUserByUsername(username)

    then:
    result == user
  }
}

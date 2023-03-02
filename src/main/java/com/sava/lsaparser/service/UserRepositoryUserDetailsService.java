package com.sava.lsaparser.service;

import com.sava.lsaparser.entity.User;
import com.sava.lsaparser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRepositoryUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    if (user != null) {
      return user;
    }
    throw new UsernameNotFoundException("User " + username + " not found");
  }
}

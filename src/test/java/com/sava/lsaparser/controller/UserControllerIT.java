package com.sava.lsaparser.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.sava.lsaparser.BaseIntegrationNoAuthTest;
import com.sava.lsaparser.dto.UserDto;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
class UserControllerIT extends BaseIntegrationNoAuthTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  void testGetUsers() throws Exception {
    mockMvc.perform(get("/user"))
        .andExpect(status().isOk());
  }

  @Test
  @DatabaseSetup("/dataset/users.xml")
  @DatabaseTearDown("/dataset/cleanDb.xml")
  void testGetUsersFromDb() throws Exception {
    String res = mockMvc.perform(get("/user"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    JSONArray array = mapper.readValue(res, JSONArray.class);
    Assertions.assertEquals(3, array.size());
  }

  @Test
  @DatabaseSetup("/dataset/cleanDb.xml")
  void testCreateUser() throws Exception {
    String username = "johnDoe";
    String email = "johndoe@domain.com";
    String pass = "12345";
    UserDto newUser = new UserDto();
    newUser.setUsername(username);
    newUser.setEmail(email);
    newUser.setPassword(pass);

    String result = mockMvc.perform(post("/user")
            .content(mapper.writeValueAsString(newUser))
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
    UserDto created = mapper.readValue(result, UserDto.class);

    assertNotNull(created);
    assertEquals(username, created.getUsername());
    assertEquals(email, created.getEmail());
    assertEquals(pass, created.getPassword());
  }
}

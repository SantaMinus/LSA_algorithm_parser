package com.sava.lsaparser.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.sava.lsaparser.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @DatabaseSetup("/dataset/users.xml")
    @DatabaseTearDown("/dataset/cleanDb.xml")
    void testFindEmptyTable() {
        List<UserDto> users = userService.getAll();

        assertEquals(3, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
        assertEquals("user3", users.get(2).getUsername());
    }

    @Test
    @DatabaseSetup("/dataset/cleanDb.xml")
    void testCreateUser() {
        String username = "johnDoe";
        String email = "johndoe@domain.com";
        String pass = "12345";
        UserDto newUser = new UserDto();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(pass);
        UserDto created = userService.create(newUser);

        assertNotNull(created);
        assertEquals(username, created.getUsername());
        assertEquals(email, created.getEmail());
        assertEquals(pass, created.getPassword());
    }
}
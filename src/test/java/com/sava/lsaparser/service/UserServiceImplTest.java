package com.sava.lsaparser.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.sava.lsaparser.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

@SpringBootTest
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void testFindEmptyTable() {
        List<UserDto> users = userService.getAll();

        Assertions.assertEquals(0, users.size());
    }
}
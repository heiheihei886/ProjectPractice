package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("john_doe");
        existingUser.setPassword("password123");
        existingUser.setName("John Doe");
        existingUser.setAge(30);
        existingUser.setSex("Male");
        Mockito.when(userDao.getUserByName("existingUser")).thenReturn(existingUser);


    }

    @Test
    public void testProcessLogin() {
        ApiResult response;

        // Case 1: Non-existing user
        response = userService.processLogin("nonExistingUser", "password");
        assertEquals(0, response.getCode());
        assertEquals("User does not exist", response.getMessage());

        // Case 2: Incorrect password
        response = userService.processLogin("existingUser", "incorrectPassword");
        assertEquals(1, response.getCode());
        assertEquals("Password incorrect", response.getMessage());

        // Case 3: Successful login
        response = userService.processLogin("existingUser", "password123");
        assertEquals(2, response.getCode());
        assertEquals("Login successfully", response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    public void testProcessRegister() {
        ApiResult response;

        // Case 1: Register an existing user
        response = userService.processRegister("existingUser", "password", "Name", "Male", 25);
        assertEquals(1, response.getCode());
        assertEquals("User exists", response.getMessage());

        // Case 2: Register a new user
        response = userService.processRegister("newUser", "password", "Name", "Female", 30);
        assertEquals(0, response.getCode());
        assertEquals("Register successfully", response.getMessage());
    }

    @Test
    public void testDeleteUser() {
        ApiResult response;

        // Case 1: Delete a non-existing user
        response = userService.deleteUser("nonExistingUser");
        assertEquals(1, response.getCode());
        assertEquals("User does not exist", response.getMessage());

        // Case 2: Delete an existing user
        response = userService.deleteUser("existingUser");
        assertEquals(2, response.getCode());
        assertEquals("Delete patient successfully", response.getMessage());
    }

}

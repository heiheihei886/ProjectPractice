package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.AdminDao;
import com.example.demo.entity.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private AdminDao adminDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        Admin admin = new Admin();
        admin.setId(1);
        admin.setName("TestAdmin");
        admin.setPassword("TestPassword");

        // 模拟adminDao的行为
        when(adminDao.getAdminByName("TestAdmin")).thenReturn(admin);
        when(adminDao.getAdminByName("NonExistentUser")).thenReturn(null);
        when(adminDao.getAdminByName("TestAdmin")).thenReturn(admin);
    }

    @Test
    public void testProcessLoginAdminWithValidCredentials() {
        ApiResult result = adminService.processLoginAdmin("TestAdmin", "TestPassword");

        // 验证登录成功
        assertEquals(2, result.getCode());
        assertEquals("Login successfully", result.getMessage());
        assertEquals(1, result.getData());
    }

    @Test
    public void testProcessLoginAdminWithInvalidPassword() {
        ApiResult result = adminService.processLoginAdmin("TestAdmin", "IncorrectPassword");

        // 验证密码不正确
        assertEquals(1, result.getCode());
        assertEquals("Password incorrect", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    public void testProcessLoginAdminWithNonExistentUser() {
        ApiResult result = adminService.processLoginAdmin("NonExistentUser", "SomePassword");

        // 验证用户不存在
        assertEquals(0, result.getCode());
        assertEquals("User does not exist", result.getMessage());
        assertNull(result.getData());
    }
}

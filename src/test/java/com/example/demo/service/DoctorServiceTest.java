package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.DoctorDao;
import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    @MockBean
    private DoctorDao doctorDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId(1);
        existingDoctor.setName("DrJohn");
        existingDoctor.setPassword("doctor123");
        existingDoctor.setDepartment("Cardiology");
        Mockito.when(doctorDao.getDoctorByName("existingDoctor")).thenReturn(existingDoctor);
    }

    @Test
    public void testProcessLoginDoc() {
        ApiResult response;

        // Case 1: Login with a non-existing doctor
        response = doctorService.processLoginDoc("nonExistingDoctor", "password");
        assertEquals(0, response.getCode());
        assertEquals("用户不存在", response.getMessage());

        // Case 2: Incorrect password
        response = doctorService.processLoginDoc("existingDoctor", "incorrectPassword");
        assertEquals(1, response.getCode());
        assertEquals("密码错误", response.getMessage());

        // Case 3: Successful login
        response = doctorService.processLoginDoc("existingDoctor", "doctor123");
        assertEquals(2, response.getCode());
        assertEquals("登录成功", response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    public void testInsertDoc() {
        ApiResult response;

        // Case 1: Insert an existing doctor
        response = doctorService.insertDoc("existingDoctor", "password", "Department A");
        assertEquals(0, response.getCode());
        assertEquals("用户已存在", response.getMessage());

        // Case 2: Insert a new doctor
        response = doctorService.insertDoc("newDoctor", "password", "Department B");
        assertEquals(2, response.getCode());
        assertEquals("新建成功", response.getMessage());
    }

    @Test
    public void testDeleteDoc() {
        ApiResult response;

        // Case 1: Delete a non-existing doctor
        response = doctorService.deleteDoc("nonExistingDoctor");
        assertEquals(0, response.getCode());
        assertEquals("用户不存在", response.getMessage());

        // Case 2: Delete an existing doctor
        response = doctorService.deleteDoc("existingDoctor");
        assertEquals(2, response.getCode());
        assertEquals("删除成功", response.getMessage());
    }
}

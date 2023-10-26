package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.NurseDao;
import com.example.demo.entity.Nurse;
import com.example.demo.service.NurseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NurseServiceTest {

    @Autowired
    private NurseService nurseService;

    @MockBean
    private NurseDao nurseDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        Nurse existingNurse = new Nurse();
        existingNurse.setId(1);
        existingNurse.setName("Nurse123");
        existingNurse.setPassword("nursePassword");
        existingNurse.setDepartment("Cardiology");
        Mockito.when(nurseDao.getNurseByName("existingNurse")).thenReturn(existingNurse);
    }

    @Test
    public void testProcessLoginNurse() {
        ApiResult response;

        // Case 1: Login with a non-existing nurse
        response = nurseService.processLoginNurse("nonExistingNurse", "password");
        assertEquals(0, response.getCode());
        assertEquals("User does not exist", response.getMessage());

        // Case 2: Incorrect password
        response = nurseService.processLoginNurse("existingNurse", "incorrectPassword");
        assertEquals(1, response.getCode());
        assertEquals("Password incorrect", response.getMessage());

        // Case 3: Successful login
        response = nurseService.processLoginNurse("existingNurse", "nursePassword");
        assertEquals(2, response.getCode());
        assertEquals("Login successfully", response.getMessage());
        assertNotNull(response.getData());
    }

    @Test
    public void testInsertNurse() {
        ApiResult response;

        // Case 1: Insert an existing nurse
        response = nurseService.insertNurse("existingNurse", "password", "Department A");
        assertEquals(0, response.getCode());
        assertEquals("User exists", response.getMessage());

        // Case 2: Insert a new nurse
        response = nurseService.insertNurse("newNurse", "password", "Department B");
        assertEquals(2, response.getCode());
        assertEquals("Add successfully", response.getMessage());
    }

    @Test
    public void testDeleteNurse() {
        ApiResult response;

        // Case 1: Delete a non-existing nurse
        response = nurseService.deleteNurse("nonExistingNurse");
        assertEquals(0, response.getCode());
        assertEquals("User does not exist", response.getMessage());

        // Case 2: Delete an existing nurse
        response = nurseService.deleteNurse("existingNurse");
        assertEquals(2, response.getCode());
        assertEquals("Delete successfully", response.getMessage());
    }
}

package com.example.demo.service;

import com.example.demo.dao.DiagSheetDao;
import com.example.demo.entity.DiagSheet;
import com.example.demo.service.DiagSheetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DiagSheetServiceTest {

    @Autowired
    private DiagSheetService diagSheetService;

    @MockBean
    private DiagSheetDao diagSheetDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        DiagSheet diagSheet = new DiagSheet();
        diagSheet.setId(1);
        diagSheet.setName("PatientA");
        diagSheet.setPriority("High");
        diagSheet.setDate("2023-10-26");
        diagSheet.setContent("Patient's condition details");
        diagSheet.setUserid(1);
        diagSheet.setResult("Diagnosis result");
        diagSheet.setIsCheck(0);

        Mockito.when(diagSheetDao.getDiagSheetById(1)).thenReturn(diagSheet);

        List<DiagSheet> diagSheetList = new ArrayList<>();
        Mockito.when(diagSheetDao.getDiagSheetByUserid(1)).thenReturn(diagSheetList);

        List<DiagSheet> diagSheetListByDepartment = new ArrayList<>();
        Mockito.when(diagSheetDao.getDiagSheetByDepartment("Cardiology", 1)).thenReturn(diagSheetListByDepartment);
    }

    @Test
    public void testQueryDiagSheet1() {
        DiagSheet diagSheet = diagSheetService.queryDiagSheet1(1);
        assertNotNull(diagSheet);
        assertEquals(1, diagSheet.getId());
        assertEquals("PatientA", diagSheet.getName());
        assertEquals("High", diagSheet.getPriority());
        assertEquals("2023-10-26", diagSheet.getDate());
        assertEquals("Patient's condition details", diagSheet.getContent());
        assertEquals(1, diagSheet.getUserid());
        assertEquals("Diagnosis result", diagSheet.getResult());
        assertEquals(0, diagSheet.getIsCheck());
    }

    @Test
    public void testQueryDiagSheet2() {
        List<DiagSheet> diagSheetList = diagSheetService.queryDiagSheet2(1);
        assertNotNull(diagSheetList);
        // Add assertions for the returned list of diagSheet objects as needed
    }

    @Test
    public void testGetDiagSheetByDepartment() {
        List<DiagSheet> diagSheetList = diagSheetService.getDiagSheetByDepartment("Cardiology", 1);
        assertNotNull(diagSheetList);
        // Add assertions for the returned list of diagSheet objects as needed
    }

    @Test
    public void testSetDiagSheetContent() {
        // Test setDiagSheetContent method
    }

    @Test
    public void testSetDiagSheetResult() {
        // Test setDiagSheetResult method
    }

    @Test
    public void testInsertSheet() {
        // Test insertSheet method
    }
}

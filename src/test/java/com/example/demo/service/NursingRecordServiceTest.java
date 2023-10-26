package com.example.demo.service;

import com.example.demo.dao.NursingRecordDao;
import com.example.demo.entity.NursingRecord;
import com.example.demo.service.NursingRecordService;
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
public class NursingRecordServiceTest {

    @Autowired
    private NursingRecordService nursingRecordService;

    @MockBean
    private NursingRecordDao nursingRecordDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        NursingRecord nursingRecord = new NursingRecord();
        nursingRecord.setId(1);
        nursingRecord.setUserid(1);
        nursingRecord.setName("PatientA");
        nursingRecord.setDate("2023-10-26");
        nursingRecord.setPriority("High");
        nursingRecord.setContent("Nursing record details");
        nursingRecord.setDepartment("Cardiology");

        Mockito.when(nursingRecordDao.getNursingRecordById(1)).thenReturn(nursingRecord);

        List<NursingRecord> nursingRecordList = new ArrayList<>();
        Mockito.when(nursingRecordDao.getNursingRecordByDepartment("Cardiology", 1)).thenReturn(nursingRecordList);
    }

    @Test
    public void testGetNursingReordByDepartment() {
        List<NursingRecord> nursingRecordList = nursingRecordService.getNursingReordByDepartment("Cardiology", 1);
        assertNotNull(nursingRecordList);
    }

    @Test
    public void testGetNursingRecordById() {
        NursingRecord nursingRecord = nursingRecordService.getNursingRecordById(1);
        assertNotNull(nursingRecord);
    }

    @Test
    public void testInsertNursingRecord() {
        // Mock the behavior of NursingRecordDao
        Mockito.doNothing().when(nursingRecordDao).insertNursingRecord(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString()
        );

        // Call the method under test
        nursingRecordService.insertNursingRecord("New Record", "Content", "High", "2023-10-26", 1, "Cardiology");

        // Verify that the insertNursingRecord method was called with the expected arguments
        Mockito.verify(nursingRecordDao).insertNursingRecord(
                "New Record",
                "Content",
                "High",
                "2023-10-26",
                1,
                "Cardiology"
        );
    }
}

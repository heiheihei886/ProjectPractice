package com.example.demo.service;

import com.example.demo.dao.MedicalRecordDao;
import com.example.demo.entity.MedicalRecord;
import com.example.demo.service.MedicalRecordService;
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
public class MedicalRecordServiceTest {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @MockBean
    private MedicalRecordDao medicalRecordDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(1);
        medicalRecord.setUserid(1);
        medicalRecord.setName("PatientA");
        medicalRecord.setDate("2023-10-26");
        medicalRecord.setContent("Medical record details");
        medicalRecord.setPriority("High");
        medicalRecord.setDepartment("Cardiology");

        Mockito.when(medicalRecordDao.getMedicalRecordById(1)).thenReturn(medicalRecord);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        Mockito.when(medicalRecordDao.getMedicalRecordByDepartment("Cardiology")).thenReturn(medicalRecordList);

        List<MedicalRecord> medicalRecordListByUserid = new ArrayList<>();
        Mockito.when(medicalRecordDao.getMedicalRecordByUserid(1)).thenReturn(medicalRecordListByUserid);

        List<MedicalRecord> medicalRecordListByDepartment2 = new ArrayList<>();
        Mockito.when(medicalRecordDao.getMedicalRecordByDepartment2("Cardiology", 1)).thenReturn(medicalRecordListByDepartment2);
    }

    @Test
    public void testGetMedicalRecordByDepartment() {
        List<MedicalRecord> medicalRecordList = medicalRecordService.getMedicalRecordByDepartment("Cardiology");
        assertNotNull(medicalRecordList);
    }

    @Test
    public void testGetMedicalRecordByDepartment2() {
        List<MedicalRecord> medicalRecordList = medicalRecordService.getMedicalRecordByDepartment2("Cardiology", 1);
        assertNotNull(medicalRecordList);

    }

    @Test
    public void testGetMedicalRecordByUserid() {
        List<MedicalRecord> medicalRecordList = medicalRecordService.getMedicalRecordByUserid(1);
        assertNotNull(medicalRecordList);
    }

    @Test
    public void testGetMedicalRecordById() {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordById(1);
        assertNotNull(medicalRecord);
    }

    @Test
    public void testInsertMedicalRecord() {
        // Mock the behavior of MedicalRecordDao
        Mockito.doNothing().when(medicalRecordDao).insertMedicalRecord(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyInt(),
                Mockito.anyString()
        );

        // Call the method under test
        medicalRecordService.insertMedicalRecord("New Record", "Content", "High", "2023-10-26", 1, "Cardiology");

        // Verify that the insertMedicalRecord method was called with the expected arguments
        Mockito.verify(medicalRecordDao).insertMedicalRecord(
                "New Record",
                "Content",
                "High",
                "2023-10-26",
                1,
                "Cardiology"
        );

    }

}

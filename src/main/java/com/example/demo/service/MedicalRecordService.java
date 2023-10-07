package com.example.demo.service;

import com.example.demo.dao.MedicalRecordDao;
import com.example.demo.entity.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MedicalRecordService")
public class MedicalRecordService {

    @Autowired
    private MedicalRecordDao medicalRecordDao;

    public List<MedicalRecord> getMedicalRecordByDepartment(String department){
        return medicalRecordDao.getMedicalRecordByDepartment(department);
    }

    public List<MedicalRecord> getMedicalRecordByDepartment2(String department, int userid){
        return medicalRecordDao.getMedicalRecordByDepartment2(department,userid);
    }

    public List<MedicalRecord> getMedicalRecordByUserid(int userid){
        return medicalRecordDao.getMedicalRecordByUserid(userid);
    }

    public MedicalRecord getMedicalRecordById(int id){
        return medicalRecordDao.getMedicalRecordById(id);
    }

    public void insertMedicalRecord(String name, String content, String priority,
                                    String date, int userid, String department) {
        medicalRecordDao.insertMedicalRecord(name, content, priority, date, userid, department);
    }
}

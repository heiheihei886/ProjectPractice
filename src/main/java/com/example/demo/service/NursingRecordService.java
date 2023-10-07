package com.example.demo.service;

import com.example.demo.dao.NursingRecordDao;
import com.example.demo.entity.NursingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("NursingRecordService")
public class NursingRecordService {

    @Autowired
    private NursingRecordDao nursingRecordDao;

    public List<NursingRecord> getNursingReordByDepartment(String department,int userid){
        return nursingRecordDao.getNursingRecordByDepartment(department,userid);
    }

    public NursingRecord getNursingRecordById(int id){
        return nursingRecordDao.getNursingRecordById(id);
    }

    public void insertNursingRecord(String name, String content, String priority,
                                    String date, int userid, String department){
        nursingRecordDao.insertNursingRecord(name,content,priority,date,userid,department);
    }

}

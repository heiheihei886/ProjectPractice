package com.example.demo.service;

import com.example.demo.dao.AccessRecordDao;
import com.example.demo.entity.AccessRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AccessRecordService")
public class AccessRecordService {

    @Autowired
    private AccessRecordDao accessRecordDao;

    public List<AccessRecord> getAccessRecord(String department, int userid){
        return accessRecordDao.getAccessRecordByDepartment(department,userid);
    }

    public AccessRecord getAccessRecordById(int id){
        return accessRecordDao.getAccessRecordById(id);
    }

    public void insertAccessRecord(String name, String content, String priority,
                                    String date, int userid, String department){
        accessRecordDao.insertAccessRecord(name,content,priority,date,userid,department);
    }
}

package com.example.demo.service;

import com.example.demo.dao.DiagSheetDao;
import com.example.demo.entity.DiagSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("DiagSheetService")
public class DiagSheetService {
    @Autowired
    private DiagSheetDao diagSheetDao;

    public DiagSheet queryDiagSheet1 (int id){
        return diagSheetDao.getDiagSheetById(id);
    }

    public List<DiagSheet> queryDiagSheet2 (int userid){
        return diagSheetDao.getDiagSheetByUserid(userid);
    }

    //根据医生科室返回未就诊诊单列表
    public List<DiagSheet> getDiagSheetByDepartment(String department,int userid){return diagSheetDao.getDiagSheetByDepartment(department,userid);}

    //根据医生科室返回未就诊诊单列表
    public List<DiagSheet> getDiagSheetByDepartment2(String department,int userid){return diagSheetDao.getDiagSheetByDepartment2(department,userid);}


    //修改诊单病情内容
    @Transactional
    public void setDiagSheetContent(int id,String content){
        diagSheetDao.setContent(id,content);
    }

    //修改诊单诊断结果
    @Transactional//声明一个事务，才能成功修改数据库
    public void setDiagSheetResult(int id, String result){
        diagSheetDao.setResult(id,result);
    }

    //插入一个新的诊单数据,诊单id自增
    public void insertSheet(String name,String content,String department,String priority,String date,int userid){
        diagSheetDao.insertSheet(name, content, department, priority, date, userid);
    }
}

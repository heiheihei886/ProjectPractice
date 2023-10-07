package com.example.demo.dao;

import com.example.demo.entity.DiagSheet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagSheetDao {
    //根据诊单id找到诊单
    public DiagSheet getDiagSheetById (@Param("id") int id);

    //根据患者id返回该患者的所有诊单
    public List<DiagSheet> getDiagSheetByUserid (@Param("userid") int userid);

    //根据医生科室返回该科室的未诊断诊单
    public List<DiagSheet> getDiagSheetByDepartment (@Param("department") String department,@Param("userid") int userid);

    //根据医生科室返回该科室的已经诊断诊单
    public List<DiagSheet> getDiagSheetByDepartment2 (@Param("department") String department,@Param("userid") int userid);


    //根据诊单id修改诊d单内容
    public void setContent (@Param("id") int id, @Param("content") String content);

    //根据id修改诊单诊断结果
    public void setResult (@Param("id") int id, @Param("result")String result);

    //用户新插入一组诊单数据
    public void insertSheet (@Param("name") String name,
                             @Param("content")String content,
                             @Param("department")String department,
                             @Param("priority")String priority,
                             @Param("date")String date,
                             @Param("userid")int userid);
}

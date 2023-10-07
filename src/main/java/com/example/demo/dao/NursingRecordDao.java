package com.example.demo.dao;

import com.example.demo.entity.NursingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NursingRecordDao {

    public List<NursingRecord> getNursingRecordByDepartment(@Param("department") String department,int userid);

    public NursingRecord getNursingRecordById(@Param("id") int id);

    public void insertNursingRecord(@Param("name")String name,
                                   @Param("content")String content,
                                   @Param("priority")String priority,
                                   @Param("date")String date,
                                   @Param("userid")int userid,
                                   @Param("department") String department);
}

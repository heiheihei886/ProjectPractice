package com.example.demo.dao;

import com.example.demo.entity.MedicalRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordDao {

    public List<MedicalRecord> getMedicalRecordByDepartment(@Param("department") String department);

    public List<MedicalRecord> getMedicalRecordByDepartment2(@Param("department") String department,
                                                             @Param("userid") int id);

    public List<MedicalRecord> getMedicalRecordByUserid(@Param("userid") int userid);

    public MedicalRecord getMedicalRecordById(@Param("id") int id);

    public void insertMedicalRecord(@Param("name")String name,
                                    @Param("content")String content,
                                    @Param("priority")String priority,
                                    @Param("date")String date,
                                    @Param("userid")int userid,
                                    @Param("department") String department);
}

package com.example.demo.dao;

import com.example.demo.entity.AccessRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessRecordDao {

    public List<AccessRecord> getAccessRecordByDepartment(@Param("department") String department,
                                                          @Param("userid")int userid);

    public AccessRecord getAccessRecordById(@Param("id") int id);

    public void insertAccessRecord(@Param("name")String name,
                                    @Param("content")String content,
                                    @Param("priority")String priority,
                                    @Param("date")String date,
                                    @Param("userid")int userid,
                                    @Param("department") String department);
}

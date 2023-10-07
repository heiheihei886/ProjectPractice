package com.example.demo.dao;

import com.example.demo.entity.Nurse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseDao {

    public Nurse getNurseByName(@Param("name") String name);

    public void insertNurse(@Param("name") String name,
                            @Param("password")String password,
                            @Param("department")String department);

    public void deleteNurse(@Param("name")String name);
}

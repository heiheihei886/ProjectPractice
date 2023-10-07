package com.example.demo.dao;

import com.example.demo.entity.Doctor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//用来注解接口，springboot自动生成一个bean
public interface DoctorDao {

    public Doctor getDoctorByName(@Param("name") String name);

    public void insertDoc(@Param("name")String name,
                          @Param("password")String password,
                          @Param("department")String department);


    public void deleteDoc(@Param("name") String name);
}

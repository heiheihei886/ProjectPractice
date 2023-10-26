package com.example.demo.dao;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {

    public Admin getAdminByName(@Param("name") String name);

}

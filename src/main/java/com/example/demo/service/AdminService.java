package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.AdminDao;
import com.example.demo.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminService")
public class AdminService {

    @Autowired
    private AdminDao adminDao;


    public ApiResult processLoginAdmin(String name, String password){
        Admin admin = adminDao.getAdminByName(name);
        ApiResult response = new ApiResult();
        if(admin == null){
            response.setCode(0);
            response.setMessage("User does not exist");
            return response;
        }else if(!admin.getPassword().equals(password)){
            response.setCode(1);
            response.setMessage("Password incorrect");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("Login successfully");
            response.setData(admin.getId());
            return response;
        }
    }

}

package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.DoctorDao;
import com.example.demo.dao.NurseDao;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.Nurse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("NurseService")
public class NurseService {

    @Autowired
    private NurseDao nurseDao;

//    //根据护士名称返回护士实体
//    public Nurse getNurseByName(String name){
//        return nurseDao.getNurseByName(name);
//    }

    public ApiResult processLoginNurse(String name, String password){
        Nurse nurse = nurseDao.getNurseByName(name);
        ApiResult response = new ApiResult();
        if(nurse == null){
            response.setCode(0);
            response.setMessage("用户不存在");
            return response;
        }else if(!nurse.getPassword().equals(password)){
            response.setCode(1);
            response.setMessage("密码错误");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("登录成功");
            response.setData(nurse.getDepartment());
            return response;
        }
    }

    public ApiResult insertNurse(String name, String password, String department){
        Nurse nurse = nurseDao.getNurseByName(name);
        ApiResult response = new ApiResult();
        if(nurse != null){
            response.setCode(0);
            response.setMessage("用户已存在");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("新建成功");
            nurseDao.insertNurse(name, password, department);
            return response;
        }
    }

    public ApiResult deleteNurse(String name){
        Nurse nurse = nurseDao.getNurseByName(name);
        ApiResult response = new ApiResult();
        if(nurse == null){
            response.setCode(0);
            response.setMessage("用户不存在");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("删除成功");
            nurseDao.deleteNurse(name);
            return response;
        }
    }

}

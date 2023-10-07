package com.example.demo.service;


import com.example.demo.ApiResult;
import com.example.demo.dao.DoctorDao;
import com.example.demo.entity.Doctor;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("DoctorService")
public class DoctorService {

    @Autowired
    private DoctorDao doctorDao;

//    public Doctor getDoctorByName(String name){
//        return doctorDao.getDoctorByName(name);
//    }

    public ApiResult processLoginDoc(String name, String password){
        Doctor doctor = doctorDao.getDoctorByName(name);
        ApiResult response = new ApiResult();
        if(doctor == null){
            response.setCode(0);
            response.setMessage("用户不存在");
            return response;
        }else if(!doctor.getPassword().equals(password)){
            response.setCode(1);
            response.setMessage("密码错误");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("登录成功");
            response.setData(doctor.getDepartment());
            return response;
        }
    }

    public ApiResult insertDoc(String name, String password, String department){
        Doctor doctor = doctorDao.getDoctorByName(name);
        ApiResult response= new ApiResult();
        if(doctor != null){
            response.setCode(0);
            response.setMessage("用户已存在");
            return response;
        }
        else{
            response.setCode(2);
            response.setMessage("新建成功");
            doctorDao.insertDoc(name, password, department);
            return response;
        }
    }

    public ApiResult deleteDoc (String name){
        Doctor doctor = doctorDao.getDoctorByName(name);
        ApiResult response = new ApiResult();
        if(doctor == null){
            response.setCode(0);
            response.setMessage("用户不存在");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("删除成功");
            doctorDao.deleteDoc(name);
            return response;
        }
    }

}
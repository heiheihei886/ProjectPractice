package com.example.demo.service;

import com.example.demo.ApiResult;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserService {


    @Autowired
    private UserDao userDao;

    public int getUserid(String username){
        return userDao.getUserid(username);
    }

    public User getUserById(int id){
        return userDao.getUserById(id);
    }

    //返回患者列表
    public List<User> getUserList(String department){
        return userDao.getUserList(department);
    }

    //返回待诊患者列表
    public List<User> getUserList2(String department){
        return userDao.getUserList2(department);
    }

    public List<User> getUserList3(String department){
        return userDao.getUserList3(department);
    }


    public User getUserByName(String username){
        return userDao.getUserByName(username);
    }

    public ApiResult processLogin(String username, String password){
        User user = userDao.getUserByName(username);
        ApiResult response = new ApiResult();
        if(user == null){
            response.setCode(0);
            response.setMessage("用户不存在");
            return response;
        }else if(!user.getPassword().equals(password)){
            response.setCode(1);
            response.setMessage("密码错误");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("登录成功");
            response.setData(user.getId());
            return response;
        }

    }

    public ApiResult processRegister(String username,String password,String name,String sex,int age){
        User user = userDao.getUserByName(username);
        ApiResult response = new ApiResult();
        if(user == null){
            response.setCode(0);
            response.setMessage("注册成功");
            userDao.processRegister(username,password,name,sex,age);
            return response;
        }else{
            response.setCode(1);
            response.setMessage("用户已存在");
            return response;
        }
    }

    public ApiResult deleteUser(String name){
        User user = userDao.getUserByName(name);
        ApiResult response = new ApiResult();
        if(user == null){
            response.setCode(1);
            response.setMessage("用户不存在");
            return response;
        }else{
            response.setCode(2);
            response.setMessage("删除患者成功");
            userDao.deleteUser(name);
            return response;
        }
    }

}



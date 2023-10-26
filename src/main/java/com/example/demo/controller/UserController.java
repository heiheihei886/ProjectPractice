package com.example.demo.controller;

import com.example.demo.ApiResult;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserid")
    public int getUserid(@RequestParam("username") String username) {
        return userService.getUserid(username);
    }

    //按患者id返回患者
    @GetMapping("/getUser")
    public User getUser(@RequestParam("id") int id) {
        return userService.getUserById(id);
    }

    //护士按科室获取患者列表
    @GetMapping("/getUserList")
    public List<User> getUserList(@RequestParam("department") String department){
        return userService.getUserList(department);
    }

    //未诊断患者列表
    @GetMapping("/getUserList2")
    public List<User> getUserList2(@RequestParam("department") String department){
        return userService.getUserList2(department);
    }

    //已诊断患者列表
    @GetMapping("/getUserList3")
    public List<User> getUserList3(@RequestParam("department") String department){
        return userService.getUserList3(department);
    }


    @GetMapping("/getUserByName")
    public  User getUserByName(@RequestParam("username") String username){return userService.getUserByName(username);}

    @GetMapping("/login")
    public ApiResult processLogin(@RequestParam("username") String username,
                                  @RequestParam("password") String password){
        System.out.println("Login successfully");
        return userService.processLogin(username, password);
    }

    @GetMapping("/register")
    public ApiResult processRegister(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("name") String name,
                                     @RequestParam("sex") String sex,
                                     @RequestParam("age") int age){
        System.out.println("Register successfully");
        return userService.processRegister(username,password,name,sex,age);
    }

    @GetMapping("/deleteUser")
    public ApiResult deleteUser(@RequestParam("name")String name){
        System.out.println("Delete patient successfully");
        return userService.deleteUser(name);
    }
}
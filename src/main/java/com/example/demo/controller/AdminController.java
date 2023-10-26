package com.example.demo.controller;

import com.example.demo.ApiResult;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/loginAdmin")
    public ApiResult processLoginAdmin(@RequestParam("name")String name,
                                       @RequestParam("password")String password){
        System.out.println("Admin login successfully");
        return adminService.processLoginAdmin(name,password);
    }

}

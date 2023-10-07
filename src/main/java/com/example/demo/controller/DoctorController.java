package com.example.demo.controller;

import com.example.demo.ApiResult;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/loginDoc")
    public ApiResult processLoginDoc (@RequestParam("name") String name,
                                      @RequestParam("password") String password){
        return doctorService.processLoginDoc(name,password);
    }

    @GetMapping("/insertDoc")
    public ApiResult insertDoc(@RequestParam("name")String name,
                               @RequestParam("password")String password,
                               @RequestParam("department")String department){
        System.out.println("新建医生成功");
        return doctorService.insertDoc(name, password, department);
    }

    @GetMapping("/deleteDoc")
    public ApiResult deleteDoc(@RequestParam("name")String name){
        System.out.println("删除医生成功");
        return doctorService.deleteDoc(name);
    }
}

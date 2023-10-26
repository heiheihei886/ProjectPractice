package com.example.demo.controller;

import com.example.demo.ApiResult;
import com.example.demo.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @GetMapping("/loginNurse")
    public ApiResult processLoginNurse(@RequestParam("name")String name,
                                       @RequestParam("password")String password){
        System.out.println("Nurse login successfully");
        return nurseService.processLoginNurse(name,password);
    }

    @GetMapping("/insertNurse")
    public ApiResult insertNurse(@RequestParam("name")String name,
                                 @RequestParam("password")String password,
                                 @RequestParam("department")String department){
        System.out.println("Add nurse successfully");
        return nurseService.insertNurse(name, password, department);
    }

    @GetMapping("/deleteNurse")
    public ApiResult deleteNurse(@RequestParam("name")String name){
        System.out.println("Delete nurse successfully");
        return nurseService.deleteNurse(name);
    }
}

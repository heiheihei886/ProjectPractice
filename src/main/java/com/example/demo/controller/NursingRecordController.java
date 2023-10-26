package com.example.demo.controller;

import com.example.demo.entity.NursingRecord;
import com.example.demo.service.NursingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NursingRecordController {

    @Autowired
    private NursingRecordService nursingRecordService;

    @GetMapping("/getNursingRecordByDepartment")
    public List<NursingRecord> getNursingRecordByDepartment(@RequestParam("department") String department,
                                                            @RequestParam("userid") int userid){
        System.out.println("Nursing records obtained successfully");
        return nursingRecordService.getNursingReordByDepartment(department,userid);
    }

    @GetMapping("/getNursingRecordById")
    public NursingRecord getNursingRecordById(@RequestParam("id") int id){
        System.out.println("Nursing records display");
        return nursingRecordService.getNursingRecordById(id);
    }

    @GetMapping("/insertNursingRecord")
    public void insertNursingRecord(@RequestParam("name") String name,
                                    @RequestParam("content") String content,
                                    @RequestParam("priority") String priority,
                                    @RequestParam("date") String date,
                                    @RequestParam("userid") int userid,
                                    @RequestParam("department") String department){

        nursingRecordService.insertNursingRecord(name, content, priority, date, userid, department);

    }
}

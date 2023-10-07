package com.example.demo.controller;

import com.example.demo.entity.AccessRecord;
import com.example.demo.service.AccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccessRecordController {

    @Autowired
    private AccessRecordService accessRecordService;

    @GetMapping("/getAccessRecordByDepartment")
    public List<AccessRecord> getAccessRecordByDepartment(@RequestParam("department") String department,
                                                          @RequestParam("userid") int userid){
        return accessRecordService.getAccessRecord(department,userid);
    }

    @GetMapping("/getAccessRecordById")
    public AccessRecord getAccessRecordById(@RequestParam("id") int id){
        return accessRecordService.getAccessRecordById(id);
    }

    @GetMapping("/insertAccessRecord")
    public void insertAccessRecord(@RequestParam("name") String name,
                                    @RequestParam("content") String content,
                                    @RequestParam("priority") String priority,
                                    @RequestParam("date") String date,
                                    @RequestParam("userid") int userid,
                                    @RequestParam("department") String department){
        accessRecordService.insertAccessRecord(name, content, priority, date, userid, department);
    }
}

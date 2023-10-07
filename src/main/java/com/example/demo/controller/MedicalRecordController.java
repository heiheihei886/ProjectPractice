package com.example.demo.controller;

import com.example.demo.entity.MedicalRecord;
import com.example.demo.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/getMedicalRecordByDepartment")
    public List<MedicalRecord> getMedicalRecordByDepartment(@RequestParam("department") String department){
        System.out.println("配药记录获取成功");
        return medicalRecordService.getMedicalRecordByDepartment(department);
    }

    @GetMapping("/getMedicalRecordByDepartment2")
    public List<MedicalRecord> getMedicalRecordByDepartment2(@RequestParam("department") String department,
                                                             @RequestParam("userid") int userid){
        return medicalRecordService.getMedicalRecordByDepartment2(department, userid);
    }

    @GetMapping("/getMedicalRecordByUserid")
    public List<MedicalRecord> getMedicalRecordByUserid(@RequestParam("userid") int userid){
        return medicalRecordService.getMedicalRecordByUserid(userid);
    }



    @GetMapping("/getMedicalRecordById")
    public MedicalRecord getMedicalRecordById(@RequestParam("id") int id){
        return medicalRecordService.getMedicalRecordById(id);
    }

    @GetMapping("/insertMedicalRecord")
    public void insertMedicalRecord(@RequestParam("name") String name,
                                    @RequestParam("content") String content,
                                    @RequestParam("priority") String priority,
                                    @RequestParam("date") String date,
                                    @RequestParam("userid") int userid,
                                    @RequestParam("department") String department){
        medicalRecordService.insertMedicalRecord(name, content, priority, date, userid, department);
    }

}

package com.example.demo.controller;

import com.example.demo.entity.DiagSheet;
import com.example.demo.service.DiagSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiagSheetController {
    @Autowired
    private DiagSheetService diagSheetService;

    //根据诊单id获取诊单信息
    @GetMapping("/getDiagSheet")
    public DiagSheet getDiagSheet1(@RequestParam("id") int id) {
        return diagSheetService.queryDiagSheet1(id);
    }

    //根据患者id获取该患者的所有诊单
    @GetMapping("/getDiagSheetList")
    public List<DiagSheet> getDiagSheet2(@RequestParam("userid") int userid){
        System.out.println("诊单列表获取成功");
        return diagSheetService.queryDiagSheet2(userid);
    }

    //根据医生科室返回未诊断诊单
    @GetMapping("getDiagSheetList_doc")
    public List<DiagSheet> getDiagSheetByDepartment(@RequestParam("department") String department,
                                                    @RequestParam("userid") int userid){
        System.out.println("未就诊诊单获取成功");
        return diagSheetService.getDiagSheetByDepartment(department,userid);
    }

    //根据医生科室返回已诊断诊单
    @GetMapping("getDiagSheetList_doc2")
    public List<DiagSheet> getDiagSheetByDepartment2(@RequestParam("department") String department,
                                                    @RequestParam("userid") int userid){
        System.out.println("已就诊诊单获取成功");
        return diagSheetService.getDiagSheetByDepartment2(department,userid);
    }


    @GetMapping("/setContent")
    public void setContent(@RequestParam("id") int id, @RequestParam("content") String content){
        diagSheetService.setDiagSheetContent(id,content);
    }

    @GetMapping("/setResult")
    public void setResult(@RequestParam("id") int id, @RequestParam("result") String result){

        System.out.println("诊断结果修改成功");
        diagSheetService.setDiagSheetResult(id,result);
    }

    @GetMapping("/insertSheet")
    public void insertSheet(@RequestParam("name") String name,
                            @RequestParam("content") String content,
                            @RequestParam("department") String department,
                            @RequestParam("priority") String priority,
                            @RequestParam("date") String date,
                            @RequestParam("userid") int userid){
        diagSheetService.insertSheet(name,content,department,priority,date,userid);
        System.out.println("诊单新建成功");
    }



}

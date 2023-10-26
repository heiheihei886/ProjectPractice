package com.example.demo.service;

import com.example.demo.entity.AccessRecord;
import com.example.demo.dao.AccessRecordDao;
import com.example.demo.service.AccessRecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccessRecordServiceTest {

    @Autowired
    private AccessRecordService accessRecordService;

    @MockBean
    private AccessRecordDao accessRecordDao;

    @BeforeEach
    public void setUp() {
        // 设置模拟数据
        AccessRecord record1 = new AccessRecord();
        record1.setId(1);
        record1.setUserid(1);
        record1.setName("Record 1");
        record1.setDate("2023-10-26");
        record1.setPriority("High");
        record1.setContent("This is the content of Record 1");
        record1.setDepartment("Department A");

        AccessRecord record2 = new AccessRecord();
        record2.setId(2);
        record2.setUserid(1);
        record2.setName("Record 2");
        record2.setDate("2023-10-27");
        record2.setPriority("Medium");
        record2.setContent("This is the content of Record 2");
        record2.setDepartment("Department B");
        Mockito.when(accessRecordDao.getAccessRecordByDepartment("Department A", 1)).thenReturn(Arrays.asList(record1));
        Mockito.when(accessRecordDao.getAccessRecordById(1)).thenReturn(record1);
    }

    @Test
    public void testGetAccessRecord() {
        List<AccessRecord> records = accessRecordService.getAccessRecord("Department A", 1);
        assertEquals(1, records.size());
        // 进一步的断言
    }

    @Test
    public void testGetAccessRecordById() {
        AccessRecord record = accessRecordService.getAccessRecordById(1);
        assertEquals(1, record.getId());
        // 进一步的断言
    }

    @Test
    public void testInsertAccessRecord() {
        // 创建一个模拟的新记录
        AccessRecord newRecord = new AccessRecord(/* 初始化参数 */);
        newRecord.setId(1);
        newRecord.setUserid(1);
        newRecord.setName("Record 1");
        newRecord.setDate("2023-10-26");
        newRecord.setPriority("High");
        newRecord.setContent("This is the content of Record 1");
        newRecord.setDepartment("Department A");
        // 插入模拟记录
        accessRecordService.insertAccessRecord(newRecord.getName(), newRecord.getContent(), newRecord.getPriority(), newRecord.getDate(), newRecord.getId(), newRecord.getDepartment());

        // 验证插入后的记录是否存在
        AccessRecord insertedRecord = accessRecordDao.getAccessRecordById(newRecord.getId());
        Assertions.assertNotNull(insertedRecord);
        // 进一步验证插入后的记录的属性是否正确
        // 进行属性断言
        assertEquals(newRecord.getName(), insertedRecord.getName());
        assertEquals(newRecord.getContent(), insertedRecord.getContent());
    }
}

package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Department;
import cn.wolfcode.service.IDepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DepartmentServiceImplTest {

    @Autowired
    private IDepartmentService departmentService;
    @Test
    void listAll() {
        List<Department> departments = departmentService.listAll();
        System.out.println("departments = " + departments);
        System.out.println(departmentService.getClass());
    }

    @Test
    void query() {
    }
}
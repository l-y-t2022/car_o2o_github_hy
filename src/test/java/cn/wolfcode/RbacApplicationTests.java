package cn.wolfcode;

import cn.wolfcode.mapper.DepartmentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class RbacApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Test
    void contextLoads() {
        System.out.println(departmentMapper);
        System.out.println(departmentMapper.selectByPrimaryKey(2l));
    }

}

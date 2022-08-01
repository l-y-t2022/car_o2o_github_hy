package cn.wolfcode.service;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IEmployeeService {
    void delete(Long id);

    void save(Employee employee, Long[] roleIds);

    void update(Employee employee, Long[] roleIds);

    Employee get(Long id);

    List<Employee> listAll();
    
    //分页查询的方法
    PageInfo<Employee> query(QueryObject qo);

    boolean queryByUsername(String username);

    Employee login(String username, String password);

    Employee queryWithUsername(String username);
}

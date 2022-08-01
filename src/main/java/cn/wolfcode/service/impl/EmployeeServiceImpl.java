package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.mapper.EmployeeMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j//该类有记日志的需求，则贴上该注解
public class EmployeeServiceImpl implements IEmployeeService {
    //在当前类记日志   等同于@Slf4j注解
    //private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public void delete(Long id) {
        employeeMapper.deleteRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Employee employee, Long[] roleIds) {
        //对密码进行加密
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getSalt(), 5);
        employee.setPassword(md5Hash.toString());
        employeeMapper.insert(employee);
        if (roleIds!=null) {
            for (Long roleId : roleIds) {
                employeeMapper.insertRelation(employee.getId(),roleId);
            }
        }
    }

    @Override
    public void update(Employee employee, Long[] roleIds) {
        employeeMapper.updateByPrimaryKey(employee);

        //先删除旧的，再保存新的
        employeeMapper.deleteRelation(employee.getId());
        if(roleIds != null && roleIds.length > 0){
            for (Long roleId : roleIds) {
                employeeMapper.insertRelation(employee.getId(),roleId);
            }
        }

    }

    @Override
    public Employee get(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    @Override
    public List<Employee> listAll() {
        List<Employee> employees = employeeMapper.selectAll();
        return employees;
    }

    @Override
    public PageInfo<Employee> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        return new PageInfo<>(employeeMapper.selectForList(qo));
    }

    @Override
    public boolean queryByUsername(String username) {
        Employee employee = employeeMapper.selectByUsername(username);
        return employee != null;
    }

    @Override
    public Employee login(String username, String password) {
        return employeeMapper.selectByUsernameAndPassword(username,password);
    }

    @Override
    public Employee queryWithUsername(String username) {
        Employee employee = employeeMapper.selectByUsername(username);
        return employee;
    }
}

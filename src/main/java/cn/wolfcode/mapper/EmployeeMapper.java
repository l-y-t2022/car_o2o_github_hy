package cn.wolfcode.mapper;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    List<Employee> selectForList(QueryObject qo);

    void deleteRelation(@Param("employee_id") Long employee_id);

    void insertRelation(@Param("employeeId") Long employeeId,@Param("roleId") Long roleId);

    Employee selectByUsername(String username);

    Employee selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
package cn.wolfcode.service;

import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRoleService {
    void delete(Long id);

    void save(Role role, Long[] permissionIds);

    void update(Role role, Long[] permissionIds);

    Role get(Long id);

    List<Role> listAll();


    //分页查询的方法
    PageInfo<Role> query(QueryObject qo);


    List<Role> queryByEmployeeId(Long employeeId);
}

package cn.wolfcode.mapper;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);


    //查询分页数据
    List<Department> selectForList(QueryObject qo);
}
package cn.wolfcode.mapper;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> selectForList(QueryObject qo);

    void insertRelation(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);


    void deleteRelation(@Param("roleId") Long roleId);

    List<Role> selectByEmployeeId(Long employeeId);
}
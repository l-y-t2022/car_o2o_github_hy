package cn.wolfcode.mapper;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.qo.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    void deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    void updateByPrimaryKey(Permission record);

    List<Permission> selectForList(QueryObject qo);

    void batchInsert(@Param("permission")Set<Permission> set);

    List<Permission> queryByRoleId(@Param("role_id") Long role_id);

    List<String> selectExpressionsByEmploeeId(Long employee_id);
}
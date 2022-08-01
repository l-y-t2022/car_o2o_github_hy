package cn.wolfcode.service;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Set;

public interface IPermissionService {
    void delete(Long id);

    void save(Permission permission);

    void update(Permission permission);

    Permission get(Long id);

    List<Permission> listAll();

    //分页查询的方法
    PageInfo<Permission> query(QueryObject qo);

    void batchInsert(Set<Permission> permissions);

    List<Permission> queryByRoleId(Long id);

    List<String> queryExpressionsByEmployeeId(Long employee_id);

    Workbook exportXLS();

    int importXLS(Workbook workbook);
}

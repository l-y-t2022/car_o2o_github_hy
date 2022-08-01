package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.domain.Permission;
import cn.wolfcode.mapper.PermissionMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public Permission get(Long id) {
        Permission permission = permissionMapper.selectByPrimaryKey(id);
        return permission;
    }

    @Override

    public List<Permission> listAll() {
        List<Permission> departments = permissionMapper.selectAll();
        return departments;
    }

    @Override
    @Transactional//贴上事务 注解

    public PageInfo<Permission> query(QueryObject qo) {
        /*int count = permission.selectForCount(qo);
        if (count==0) {
            return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count, Collections.emptyList());
        }
        return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count,permission.selectForList(qo));*/
        //对接下执行查询分页加工
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        //执行查询返回PageInfo对象(类似PageResult，里面封装分页查询数据)
        //底层会帮忙查数量
        return new PageInfo<>(permissionMapper.selectForList(qo));
    }

    @Override
    public void batchInsert(Set<Permission> permissions) {
        permissionMapper.batchInsert(permissions);
    }

    @Override
    public List<Permission> queryByRoleId(Long id) {
        List<Permission> permissions = permissionMapper.queryByRoleId(id);
        return permissions;
    }

    @Override
    public List<String> queryExpressionsByEmployeeId(Long employee_id) {
        List<String> expressions = permissionMapper.selectExpressionsByEmploeeId(employee_id);
        return expressions;
    }

    @Override
    public Workbook exportXLS() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("权限信息");
        Row headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("id");
        headRow.createCell(1).setCellValue("name");
        headRow.createCell(2).setCellValue("expression");
        //从数据库获取数据
        List<Permission> permissions = permissionMapper.selectAll();
        for (int i = 0; i < permissions.size(); i++) {
            //获取每一行的数据
            Permission permission = permissions.get(i);
            //写入excel中
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(permission.getId());
            row.createCell(1).setCellValue(permission.getName());
            row.createCell(2).setCellValue(permission.getExpression());
        }
        return workbook;
    }

    @Override
    public int importXLS(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        int count = 0;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            //获取行
            Row row = sheet.getRow(i+1);
            //获取单元格的值
            Cell oneCell = row.getCell(1);
            String name = oneCell.getStringCellValue();
            Cell twoCell = row.getCell(2);
            String expression = twoCell.getStringCellValue();
            Permission permission = new Permission(name,expression);
            int insert = permissionMapper.insert(permission);
            count+=insert;
        }
        return count;
    }
}

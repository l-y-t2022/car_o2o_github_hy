package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Department;
import cn.wolfcode.mapper.DepartmentMapper;

import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public void update(Department department) {
        departmentMapper.updateByPrimaryKey(department);
    }

    @Override
    public Department get(Long id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
        return department;
    }

    @Override

    public List<Department> listAll() {
        List<Department> departments = departmentMapper.selectAll();
        return departments;
    }

    @Override
    @Transactional//贴上事务 注解

    public PageInfo<Department> query(QueryObject qo) {
        /*int count = departmentMapper.selectForCount(qo);
        if (count==0) {
            return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count, Collections.emptyList());
        }
        return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count,departmentMapper.selectForList(qo));*/
        //对接下执行查询分页加工
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        //执行查询返回PageInfo对象(类似PageResult，里面封装分页查询数据)
        //底层会帮忙查数量
        return new PageInfo<>(departmentMapper.selectForList(qo));
    }

    @Override
    public Workbook export() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("部门信息");
        //设置表头
        //创建第一行，设置列头
        Row head = sheet.createRow(0);
        head.createCell(0).setCellValue("id");
        head.createCell(1).setCellValue("name");
        head.createCell(2).setCellValue("sn");
        //负责在内存中创建excel文件，并向文件写入部门数据
        List<Department> departments = departmentMapper.selectAll();
        //每遍历到一个部门，往excel中插入一行
        for (int i = 0; i < departments.size(); i++) {
            Row row = sheet.createRow(i+1);
            Department department = departments.get(i);
            row.createCell(0).setCellValue(department.getId());
            row.createCell(1).setCellValue(department.getName());
            row.createCell(2).setCellValue(department.getSn());
        }
        return workbook;
    }

    @Override
    public int importExcel(Workbook workbook) {
        //利用poi读取文件，存到数据库中
        Sheet sheet = workbook.getSheetAt(0);

        int lastRowNum = sheet.getLastRowNum();
        int count = 0;
        //从1开始
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String name = row.getCell(1).getStringCellValue();
            String sn = row.getCell(2).getStringCellValue();
            Department department = new Department();
            department.setSn(sn);
            department.setName(name);
            //1 或 0
            int affectedCount = departmentMapper.insert(department);
            count+=affectedCount;
        }
        //返回受影响的行数
        return count;
    }
}

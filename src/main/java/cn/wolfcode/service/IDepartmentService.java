package cn.wolfcode.service;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface IDepartmentService {
    void delete(Long id);

    void save(Department department);

    void update(Department department);

    Department get(Long id);

    List<Department> listAll();


    //分页查询的方法
    PageInfo<Department> query(QueryObject qo);

    Workbook export();

    int importExcel(Workbook workbook);
}

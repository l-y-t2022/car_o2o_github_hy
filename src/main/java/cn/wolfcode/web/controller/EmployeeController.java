package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.Employee;
import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.EmployeeQueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IRoleService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;
    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    @RequiresPermissions(value = {"employee:list","员工显示"},logical = Logical.OR)
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo){
        //查询数据
        PageInfo<Employee> pageInfo = employeeService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        //查询部门，存到Model中
        List<Department> departments = departmentService.listAll();
        model.addAttribute("departments",departments);
        //返回视图文件
        return "employee/list";
    }

    //处理删除请求
    //  /employee/delete?id=
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"employee:delete","员工删除"},logical = Logical.OR)
    public String delete(Long id){
        if(id != null) {
            employeeService.delete(id);
        }
        return "redirect:/employee/list";
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    @RequiresPermissions(value = {"employee:saveOrUpdate","员工新增或修改"},logical = Logical.OR)
    public String input(Long id, Model model){
        List<Department> departments = departmentService.listAll();
        model.addAttribute("departments",departments);

        //传所有角色。存在model
        List<Role> roles = roleService.listAll();
        if(id != null) {
            Employee employee = employeeService.get(id);
            model.addAttribute("employee",employee);

            //查询被修改员工所拥有的角色存到model----------
            List<Role> ownRoles = roleService.queryByEmployeeId(id);
            model.addAttribute("ownRoles",ownRoles);
            System.out.println(ownRoles);

            //去重
            roles.removeAll(ownRoles);
        }
        model.addAttribute("roles",roles);
        return "employee/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"employee:saveOrUpdate","员工新增或修改"},logical = Logical.OR)
    public String saveOrUpdate(Employee employee,Long[] roleIds) {
        if (employee.getId() == null) {
            //新增
            employeeService.save(employee,roleIds);
        } else {
            //修改
            employeeService.update(employee,roleIds);
        }
        return "redirect:/employee/list";
    }

    @RequestMapping("/checkUserName")
    @ResponseBody//响应json
    public Map<String,Boolean> checkUserName(String username){
        HashMap<String, Boolean> map = new HashMap<>();
        boolean exist = employeeService.queryByUsername(username);
        map.put("valid",!exist);
        return map;
    }
}

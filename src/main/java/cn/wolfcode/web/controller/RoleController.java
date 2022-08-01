package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.service.IRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;
    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    public String list(Model model, QueryObject qo){
        //查询数据
        PageInfo<Role> pageInfo = roleService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        //返回视图文件
        return "role/list";
    }


    //处理删除请求
    //  /role/delete?id=
    @RequestMapping("/delete")
    public String delete(Long id){
        if(id != null) {
            roleService.delete(id);
        }
        return "redirect:/role/list";
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    public String input(Long id, Model model){
        //查询所有的权限数据，存到model里面
        List<Permission> permissions = permissionService.listAll();
        model.addAttribute("permissions",permissions);

        if(id != null) {
            Role role = roleService.get(id);
            model.addAttribute("role",role);

            //查询这个修改角色所拥有的权限，存到model中
            List<Permission> ownPermission=permissionService.queryByRoleId(role.getId());
            model.addAttribute("ownPermission",ownPermission);
            //去重
            permissions.removeAll(ownPermission);
        }
        return "role/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role,Long[] permissionIds) {
        if (role.getId() == null) {
            //新增
            roleService.save(role,permissionIds);
        } else {
            //修改
            roleService.update(role,permissionIds);
        }
        return "redirect:/role/list";
    }



}

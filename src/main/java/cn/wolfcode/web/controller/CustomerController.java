package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.domain.SysDictData;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ICustomerService;
import cn.wolfcode.service.ISysDictDataService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ISysDictDataService sysDictDataService;
    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    @RequiresPermissions(value = {"customer:list","部门显示"},logical = Logical.OR)
    public String list(Model model, QueryObject qo){

        //查询数据
        PageInfo<Customer> pageInfo = customerService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        //返回视图文件
        return "customer/list";
    }


    //处理删除请求
    //  /customer/delete?id=
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"customer:delete","部门删除"},logical = Logical.OR)
    public String delete(Long id){
        if(id != null) {
            customerService.delete(id);
        }
        return "redirect:/customer/list";
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    @RequiresPermissions(value = {"customer:saveOrUpdate","部门新增或修改"},logical = Logical.OR)
    public String input(Long id, Model model){
        //去数据库查询来源渠道
        List<SysDictData> sources = sysDictDataService.queryByType("source");
        model.addAttribute("sources",sources);
        //去数据库查询意向校区
        List<SysDictData> schools = sysDictDataService.queryByType("school");
        model.addAttribute("schools",schools);

        if(id != null) {
            Customer customer = customerService.get(id);
            model.addAttribute("customer",customer);
        }
        return "customer/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"customer:saveOrUpdate","部门新增或修改"},logical = Logical.OR)
    public String saveOrUpdate(Customer customer) {
        if (customer.getId() == null) {
            //新增
            customerService.save(customer);
        } else {
            //修改
            customerService.update(customer);
        }
        return "redirect:/customer/list";
    }



}

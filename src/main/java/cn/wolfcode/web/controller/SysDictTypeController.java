package cn.wolfcode.web.controller;

import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ISysDictTypeService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysDictType")
public class SysDictTypeController {
    @Autowired
    private ISysDictTypeService sysDictTypeService;
    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    public String list(Model model, QueryObject qo){

        //查询数据
        PageInfo<SysDictType> pageInfo = sysDictTypeService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        //返回视图文件
        return "sysDictType/list";
    }


    //处理删除请求
    //  /sysDictType/delete?id=
    @RequestMapping("/delete")
    public String delete(Long id){
        if(id != null) {
            sysDictTypeService.delete(id);
        }
        return "redirect:/sysDictType/list";
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    public String input(Long id, Model model){
        //查询sysDictType

        if(id != null) {
            SysDictType sysDictType = sysDictTypeService.get(id);
            model.addAttribute("sysDictType",sysDictType);
        }
        return "sysDictType/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(SysDictType sysDictType) {
        if (sysDictType.getId() == null) {
            //新增
            sysDictTypeService.save(sysDictType);
        } else {
            //修改
            sysDictTypeService.update(sysDictType);
        }
        return "redirect:/sysDictType/list";
    }



}

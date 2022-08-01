package cn.wolfcode.web.controller;

import cn.wolfcode.domain.SysDictData;
import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.SysDictDataQueryObject;
import cn.wolfcode.service.ISysDictDataService;
import cn.wolfcode.service.ISysDictTypeService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysDictData")
public class SysDictDataController {
    @Autowired
    private ISysDictTypeService sysDictTypeService;
    @Autowired
    private ISysDictDataService sysDictDataService;

    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    public String list(Model model, SysDictDataQueryObject qo){

        //查询数据
        PageInfo<SysDictData> pageInfo = sysDictDataService.query(qo);
        model.addAttribute("pageInfo",pageInfo);


        //提供查询下拉框名称的方法
        SysDictType sysDictType = sysDictTypeService.queryByType(qo.getType());
        model.addAttribute("sysDictType",sysDictType);
        //返回视图文件
        return "sysDictData/list";
    }


    //处理删除请求
    //  /sysDictData/delete?id=
    @RequestMapping("/delete")
    public String delete(Long id){
        if(id != null) {
            sysDictDataService.delete(id);
        }
        return "redirect:/sysDictData/list";
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    public String input(Long id, Model model,String type){
        SysDictType sysDictType = sysDictTypeService.queryByType(type);
        model.addAttribute("sysDictType",sysDictType);
        if(id != null) {
            SysDictData sysDictData = sysDictDataService.get(id);
            model.addAttribute("sysDictData",sysDictData);
        }

        return "sysDictData/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(SysDictData sysDictData) {
        if (sysDictData.getId() == null) {
            //新增
            sysDictDataService.save(sysDictData);
        } else {
            //修改
            sysDictDataService.update(sysDictData);
        }
        return "redirect:/sysDictData/list?type="+sysDictData.getType();
    }



}

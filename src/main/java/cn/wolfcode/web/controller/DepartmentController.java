package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Department;

import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.MulticastChannel;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;
    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    @RequiresPermissions(value = {"department:list","部门显示"},logical = Logical.OR)
    public String list(Model model, QueryObject qo){

        //查询数据
        PageInfo<Department> pageInfo = departmentService.query(qo);
        model.addAttribute("pageInfo",pageInfo);
        //返回视图文件
        return "department/list";
    }


    //处理删除请求
    //  /department/delete?id=
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"department:delete","部门删除"},logical = Logical.OR)
    public String delete(Long id){
        if(id != null) {
            departmentService.delete(id);
        }
        return "redirect:/department/list";
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    @RequiresPermissions(value = {"department:saveOrUpdate","部门新增或修改"},logical = Logical.OR)
    public String input(Long id, Model model){
        if(id != null) {
            Department department = departmentService.get(id);
            model.addAttribute("department",department);
        }
        return "department/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"department:saveOrUpdate","部门新增或修改"},logical = Logical.OR)
    public String saveOrUpdate(Department department) {
        if (department.getId() == null) {
            //新增
            departmentService.save(department);
        } else {
            //修改
            departmentService.update(department);
        }
        return "redirect:/department/list";
    }



    //导出
    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        //下载的文件名在filename后面
        response.setHeader("Content-Disposition","attachment;filename=write03.xls");

        //excel文件在内存中
        Workbook workbook = departmentService.export();

        //写回浏览器
        //因为这个输出流是从响应对象获取到的，所以操作这个流，都是向浏览器响应数据(二进制的内容[excel是一个二进制文件])
        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);//workbook在内存中
    }

    //上传请求
    //获取文件内容
    //使用POI读取文件，存到数据库中
    @RequestMapping("/importExcel")
    @ResponseBody
    public JsonResult importExcel(MultipartFile file) throws IOException {
        //获取文件输入流
        InputStream is = file.getInputStream();
        Workbook workbook = new HSSFWorkbook(is);
        int count = 0;
        try {
            count = departmentService.importExcel(workbook);
        } catch (Exception e) {
            return new JsonResult(false,"导入失败");
        }

        return new JsonResult(true,"导入"+count+"条数据");
    }

}

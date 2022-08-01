package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.domain.Permission;
import cn.wolfcode.domain.TableDataInfo;
import cn.wolfcode.qo.JsonResult;
import cn.wolfcode.qo.PermissionQueryObject;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private ApplicationContext ctx;

    //处理查询所有的请求,响应html
    //分页查询的数据
    @RequestMapping("/list")
    public String list(Model model, QueryObject qo){
        /*//查询数据
        PageInfo<Permission> pageInfo = permissionService.query(qo);
        model.addAttribute("pageInfo",pageInfo);*/
        //返回视图文件
        return "permission/list";
    }

    @RequestMapping("/listData")
    @ResponseBody
    public TableDataInfo listData(PermissionQueryObject qo){
        //实现分页
        PageHelper.offsetPage(qo.getOffset(),qo.getLimit());
        PageInfo<Permission> pageInfo = permissionService.query(qo);
        return new TableDataInfo(pageInfo.getTotal(),pageInfo.getList());
    }

    /*新增  修改*/
    //处理 去 新增/修改(带id)
    @RequestMapping("/input")
    @RequiresPermissions(value = {"permission:saveOrUpdate","部门新增或修改"},logical = Logical.OR)
    public String input(Long id, Model model){
        if(id != null) {
            Permission permission = permissionService.get(id);
            model.addAttribute("permission",permission);
        }
        return "permission/input";
    }

    //处理 新增/修改(带id)
    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"permission:saveOrUpdate","部门新增或修改"},logical = Logical.OR)
    public String saveOrUpdate(Permission permission) {
        if (permission.getId() == null) {
            //新增
            permissionService.save(permission);
        } else {
            //修改
            permissionService.update(permission);
        }
        return "redirect:/permission/list";
    }


    //处理删除请求
    //  /role/delete?id=
    @RequestMapping("/delete")
    public String delete(Long id){
        if(id != null) {
            permissionService.delete(id);
        }
        return "redirect:/permission/list";
    }

    //加载权限处理放方法,响应JSON
    @RequestMapping("/load")
    @ResponseBody
    public JsonResult load(){
        //加载权限的代码,把处理方法转为数据库中权限表中一条数据

        //自定义注解，贴自定义注解

        //如何获取到所有的控制器的处理方法
            //获取Spring容器对象,从容器中获取控制器对象
            //获取这些处方法所有类字节码对象
                //PermissionController.class不能选这个，写死，以后加控制器或减少控制器，得修改这里的代码
                //Class.forName("cn.wolfcode.web.controller.PermissionController"),ue不能选这个，也是写死
                //PermissionController对象.getClass()选这个，这个对象让容器创建，新加或者减少控制器Spring容器中也有更新

            //处理方法Method对象(里面封装方法相关信息，方法名，返回值，修改符，注解，形参)
        //每拿到一个封装为Permission，调用permissionService对象保存方法存到数据库中


        //第一个重复问题，当贴了相同的注解，导致了重复
            //修改设计表，设置唯一，因为执行出错，导致下面执行代码会执行不了，一般部推荐用数据库来检查数据合理性(耦合数据库)，性能更差一些
            //用代码判断数据合理性
                //保存到set(自定义比较规则，重写equals和hashcode)中，定义去重，再存入数据库中

        //第二个重复问题，再点击重新加载权限按钮导致重复
            //在保存之前，一次行把所有权限数据查询出来List，再插入之前跟list比对，若不存在则存到数据库中

       /* List<Permission> allPermissions = permissionService.listAll();

        Set<Permission> permissions = new LinkedHashSet<>();
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(Controller.class);
        Collection<Object> controllers = beanMap.values();
        for (Object controller : controllers) {
            Method[] declaredMethods = controller.getClass().getSuperclass().getDeclaredMethods();
            for (Method method : declaredMethods) {
                //获取方法上我们贴自定义注解(可能存在一些处理方法没有贴自定义注解，没有贴注解返回空)
                RequirePermission annotation = method.getAnnotation(RequirePermission.class);
                if (annotation!=null) {
                    String name = annotation.name();
                    String expression = annotation.expression();
                    Permission permission = new Permission();
                    permission.setName(name);
                    permission.setExpression(expression);
                    if (!allPermissions.contains(permission)) {
                        //加入set
                        permissions.add(permission);
                    }
                }
                System.out.println(permissions);
            }
        }
*/
       //创建一个set集合
        LinkedHashSet<Permission> set = new LinkedHashSet<>();

        //查询所有权限
        List<Permission> permissions = permissionService.listAll();
        //1，获取Spring容器对象
        Map<String, Object> ctxBeansWithAnnotation = ctx.getBeansWithAnnotation(Controller.class);
        //2.获取控制器对象的map
        Collection<Object> controllers = ctxBeansWithAnnotation.values();
        for (Object controller : controllers) {
            //获取每个控制器对象的方法
            Method[] declaredMethods = controller.getClass().getSuperclass().getDeclaredMethods();

            for (Method declaredMethod : declaredMethods) {
                RequiresPermissions annotation = declaredMethod.getAnnotation(RequiresPermissions.class);
                if (annotation!=null) {
                    String[] expressions = annotation.value();
                    Permission permission = new Permission();
                    if (expressions!=null) {
                        String name = expressions[1];
                        String expression = expressions[0];
                        permission.setName(name);
                        permission.setExpression(expression);
                    }
                    if (!permissions.contains(permission)) {
                        set.add(permission);
                    }
                }
            }
        }
        if (set!=null) {
            permissionService.batchInsert(set);
        }
        //3.获取map的值，即控制器对象

        //4.通过控制器对象获取方法，再获取注解

        //5.获取注解的值，创建Permission对象

        //6.存入set集合，去重

        //7.数据库查询所有权限

        //8.存入数据库



        //lambda表达式
      /*  Set<Permission> permissions = ctx.getBeansWithAnnotation(Controller.class).values().stream()
                .flatMap(controller -> Arrays.stream(controller.getClass().getDeclaredMethods()))
                .filter(method -> method.getAnnotation(RequirePermission.class) != null)
                .map(method -> new Permission(method.getAnnotation(RequirePermission.class).name(), method.getAnnotation(RequirePermission.class).expression()))
                .filter(permission -> !allPermissions.contains(permission))
                .collect(Collectors.toSet());//把流中的元素用set存*/



     /*   //循环结束之后，批量保存
        if (permissions.size()>0) {
            permissionService.batchInsert(permissions);
        }*/
        return new JsonResult(true,"加载成功");
    }


    @RequestMapping("/nopermission")
    public String nopermission(){
        return "common/nopermission";
    }
    @RequestMapping("/test")
    public String test(HttpSession session){
        session.setAttribute("Msg","hello");
        return "test";
    }


    //实现导出功能
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        //设置头部信息以及下载的文件名
        response.setHeader("Content-Disposition", "attachment;filename=permission.xls");
        //利用workbook从数据库中读取数据存到excel文件中

        Workbook workbook = permissionService.exportXLS();
        //将数据响应回浏览器
        workbook.write(response.getOutputStream());
    }


    @RequestMapping("/importExcel")
    @ResponseBody
    public JsonResult importExcel(MultipartFile file) throws IOException {
        Workbook workbook = new HSSFWorkbook(file.getInputStream());
        int count = 0;
        //将数据写入数据库,返回受影响的行数
        try {
            count = permissionService.importXLS(workbook);
            return new JsonResult(true,"导入成功，受影响"+count+"行");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false,"导入失败");
        }

    }

}

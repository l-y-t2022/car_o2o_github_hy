package cn.wolfcode.realm;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.service.IRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

//自定义Realm，提供缓存功能，提供认证数据，提供授权数据
public class EmployeeRealm extends AuthorizingRealm {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionServicel;

    /**
     *  提供认证的方法,方法体根据自己的需求来写
     * @param usernamePasswordToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken usernamePasswordToken) throws AuthenticationException {
        //从数据库中查询数据
        UsernamePasswordToken token = (UsernamePasswordToken) usernamePasswordToken;
        String username = token.getUsername();
        //根据员工的用户名查询员工对象
        Employee employee = employeeService.queryWithUsername(username);
        if (employee==null) {
            return null;
        }
        //加密认证
        return new SimpleAuthenticationInfo(employee,employee.getPassword(), ByteSource.Util.bytes(employee.getSalt()),this.getName());
    }

    /**
     *  //提供授权的方法,根据自己的需求来写
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取访问的员工
        Employee employee = (Employee) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //判断员工是否为管理员
        if (employee.isAdmin()) {//管理员
            //去数据库中查询所有的角色和权限
            List<String> roles = roleService.listAll().stream()
                    .map(role -> role.getSn())
                    .collect(Collectors.toList());
           /* List<String> expressions = permissionServicel.listAll().stream()
                    .map(permission -> permission.getExpression())
                    .collect(Collectors.toList());*/
            info.addRoles(roles);
            info.addStringPermission("*:*");//代表该员工拥有所有的权限，权限表达式支持通配符
            return info;
        }
        //获取员工id的角色和权限
        List<String> roles = roleService.queryByEmployeeId(employee.getId()).stream()
                .map(role -> role.getSn())
                .collect(Collectors.toList());

        List<String> expressions = permissionServicel.queryExpressionsByEmployeeId(employee.getId());

        info.addRoles(roles);
        info.addStringPermissions(expressions);

        return info;
    }


}

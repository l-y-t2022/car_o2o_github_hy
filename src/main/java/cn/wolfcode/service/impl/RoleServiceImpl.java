package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Role;
import cn.wolfcode.mapper.RoleMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Role role, Long[] permissionIds) {
        roleMapper.insert(role);
        //往role_permission中存数据
        if (permissionIds!=null && permissionIds.length>0) {
            for (Long permissionId : permissionIds) {
                roleMapper.insertRelation(role.getId(),permissionId);
            }
        }
    }

    @Override
    public void update(Role role, Long[] permissionIds) {
        roleMapper.updateByPrimaryKey(role);

        //删除旧的数据，插入新 的数据
        roleMapper.deleteRelation(role.getId());
        if(permissionIds!=null && permissionIds.length>0){
            for (Long permissionId : permissionIds) {
                roleMapper.insertRelation(role.getId(),permissionId);
            }
        }
    }

    @Override
    public Role get(Long id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }

    @Override

    public List<Role> listAll() {
        List<Role> roles = roleMapper.selectAll();
        return roles;
    }

    @Override
    @Transactional//贴上事务 注解

    public PageInfo<Role> query(QueryObject qo) {
        /*int count = roleMapper.selectForCount(qo);
        if (count==0) {
            return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count, Collections.emptyList());
        }
        return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count,roleMapper.selectForList(qo));*/
        //对接下执行查询分页加工
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        //执行查询返回PageInfo对象(类似PageResult，里面封装分页查询数据)
        //底层会帮忙查数量
        return new PageInfo<>(roleMapper.selectForList(qo));
    }

    @Override
    public List<Role> queryByEmployeeId(Long employeeId) {
        List<Role> roles = roleMapper.selectByEmployeeId(employeeId);
        return roles;
    }


}

package cn.wolfcode.service.impl;

import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.mapper.SysDictTypeMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ISysDictTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDictTypeImpl implements ISysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;
    @Override
    public void delete(Long id) {
        sysDictTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(SysDictType sysDictType) {
        sysDictTypeMapper.insert(sysDictType);
    }

    @Override
    public void update(SysDictType sysDictType) {
        sysDictTypeMapper.updateByPrimaryKey(sysDictType);
    }

    @Override
    public SysDictType get(Long id) {
        SysDictType sysDictType = sysDictTypeMapper.selectByPrimaryKey(id);
        return sysDictType;
    }

    @Override

    public List<SysDictType> listAll() {
        List<SysDictType> sysDictTypes = sysDictTypeMapper.selectAll();
        return sysDictTypes;
    }

    @Override
    @Transactional//贴上事务 注解

    public PageInfo<SysDictType> query(QueryObject qo) {
        /*int count = sysDictType.selectForCount(qo);
        if (count==0) {
            return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count, Collections.emptyList());
        }
        return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count,sysDictType.selectForList(qo));*/
        //对接下执行查询分页加工
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        //执行查询返回PageInfo对象(类似PageResult，里面封装分页查询数据)
        //底层会帮忙查数量
        return new PageInfo<>(sysDictTypeMapper.selectForList(qo));
    }

    @Override
    public SysDictType queryByType(String type) {
        return sysDictTypeMapper.selectByType(type);
    }
}

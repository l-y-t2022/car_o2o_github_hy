package cn.wolfcode.service.impl;

import cn.wolfcode.domain.SysDictData;
import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.mapper.SysDictDataMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ISysDictDataService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDictDataImpl implements ISysDictDataService {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Override
    public void delete(Long id) {
        sysDictDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(SysDictData sysDictData) {
        sysDictDataMapper.insert(sysDictData);
    }

    @Override
    public void update(SysDictData sysDictData) {
        sysDictDataMapper.updateByPrimaryKey(sysDictData);
    }

    @Override
    public SysDictData get(Long id) {
        SysDictData sysDictData = sysDictDataMapper.selectByPrimaryKey(id);
        return sysDictData;
    }

    @Override

    public List<SysDictData> listAll() {
        List<SysDictData> sysDictDatas = sysDictDataMapper.selectAll();
        return sysDictDatas;
    }

    @Override
    @Transactional//贴上事务 注解

    public PageInfo<SysDictData> query(QueryObject qo) {
        /*int count = sysDictData.selectForCount(qo);
        if (count==0) {
            return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count, Collections.emptyList());
        }
        return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count,sysDictData.selectForList(qo));*/
        //对接下执行查询分页加工
        //pageSize=0，表示查询所有
        PageHelper.startPage(qo.getCurrentPage(),0);
        //执行查询返回PageInfo对象(类似PageResult，里面封装分页查询数据)
        //底层会帮忙查数量
        return new PageInfo<>(sysDictDataMapper.selectForList(qo));
    }

    @Override
    public List<SysDictData> queryByType(String type) {

        return sysDictDataMapper.selectByType(type);
    }


}

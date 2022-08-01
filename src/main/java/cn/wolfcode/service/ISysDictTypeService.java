package cn.wolfcode.service;

import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISysDictTypeService {
    void delete(Long id);

    void save(SysDictType sysDictType);

    void update(SysDictType sysDictType);

    SysDictType get(Long id);

    List<SysDictType> listAll();


    //分页查询的方法
    PageInfo<SysDictType> query(QueryObject qo);

    SysDictType queryByType(String type);
}

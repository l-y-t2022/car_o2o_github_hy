package cn.wolfcode.service;

import cn.wolfcode.domain.SysDictData;
import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISysDictDataService {
    void delete(Long id);

    void save(SysDictData sysDictData);

    void update(SysDictData sysDictData);

    SysDictData get(Long id);

    List<SysDictData> listAll();


    //分页查询的方法
    PageInfo<SysDictData> query(QueryObject qo);



    List<SysDictData> queryByType(String type);
}

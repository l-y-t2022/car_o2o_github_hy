package cn.wolfcode.mapper;

import cn.wolfcode.domain.SysDictData;
import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface SysDictDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDictData record);

    SysDictData selectByPrimaryKey(Long id);

    List<SysDictData> selectAll();

    int updateByPrimaryKey(SysDictData record);

    List<SysDictData> selectForList(QueryObject qo);

    List<SysDictData> selectByType(String type);
}
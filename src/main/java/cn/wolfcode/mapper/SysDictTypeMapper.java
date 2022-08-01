package cn.wolfcode.mapper;

import cn.wolfcode.domain.SysDictType;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface SysDictTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysDictType record);

    SysDictType selectByPrimaryKey(Long id);

    List<SysDictType> selectAll();

    int updateByPrimaryKey(SysDictType record);

    List<SysDictType> selectForList(QueryObject qo);

    SysDictType selectByType(String type);
}
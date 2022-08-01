package cn.wolfcode.mapper;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.qo.QueryObject;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll();

    int updateByPrimaryKey(Customer record);

    List<Customer> selectForList(QueryObject qo);
}
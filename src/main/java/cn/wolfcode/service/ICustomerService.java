package cn.wolfcode.service;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerService {
    void delete(Long id);

    void save(Customer customer);

    void update(Customer customer);

    Customer get(Long id);

    List<Customer> listAll();


    //分页查询的方法
    PageInfo<Customer> query(QueryObject qo);

}

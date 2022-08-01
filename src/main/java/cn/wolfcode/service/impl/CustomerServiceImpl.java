package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.mapper.CustomerMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ICustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void delete(Long id) {
        customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Customer customer) {
        customerMapper.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer get(Long id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        return customer;
    }

    @Override

    public List<Customer> listAll() {
        List<Customer> customers = customerMapper.selectAll();
        return customers;
    }

    @Override
    @Transactional//贴上事务 注解

    public PageInfo<Customer> query(QueryObject qo) {
        /*int count = customerMapper.selectForCount(qo);
        if (count==0) {
            return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count, Collections.emptyList());
        }
        return new PageResult<>(qo.getCurrentPage(),qo.getPageSize(),count,customerMapper.selectForList(qo));*/
        //对接下执行查询分页加工
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize());
        //执行查询返回PageInfo对象(类似PageResult，里面封装分页查询数据)
        //底层会帮忙查数量
        return new PageInfo<>(customerMapper.selectForList(qo));
    }
}

package cn.wolfcode.qo;

import lombok.Getter;
import lombok.Setter;

//封装过滤参数

@Getter
@Setter
public class EmployeeQueryObject extends QueryObject {
    private String keyword;
    private Long deptId;
}

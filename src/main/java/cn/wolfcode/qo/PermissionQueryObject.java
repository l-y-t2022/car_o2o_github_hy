package cn.wolfcode.qo;

import lombok.Data;

@Data
public class PermissionQueryObject extends QueryObject {
    //offset--boostrap-table的起始数据,相当于start
    private int offset;
    //limit--bookstrap-table的pageSize
    private int limit;
}

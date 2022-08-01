package cn.wolfcode.domain;

import lombok.Data;

@Data
public class Customer {
    /** */
    private Long id;

    /** */
    private String name;

    /** */
    private String phone;

    /** */
    private SysDictData source;
    private SysDictData school;


}
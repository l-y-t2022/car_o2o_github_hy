package cn.wolfcode.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Employee {
    /** */
    private Long id;

    /**用户名 */
    private String username;

    /** 姓名*/
    private String name;

    /** */
    private String password;

    /** */
    private String email;

    /** */
    private Integer age;

    /** 表示是不是管理员  表格里面的数据类型bit  对应0和1  true--->1*/
    /*必填：则为基本数据类型，不为null*/
    private boolean admin;

    /** 部门id*/
    private Department dept;

    private String salt= UUID.randomUUID().toString().substring(0,4);//盐
}
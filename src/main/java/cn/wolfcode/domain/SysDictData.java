package cn.wolfcode.domain;

import lombok.Data;

@Data
public class SysDictData {
    /** */
    private Long id;

    /** */
    private String type;

    /** */
    private String label;

    /** */
    private Integer value;

    /** */
    private Integer sort;

    /** */
    private String remark;


}
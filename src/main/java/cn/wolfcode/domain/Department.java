package cn.wolfcode.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class Department {
    /** */
    private Long id;

    /** */
    private String name;

    /** */
    private String sn;
    //每个部门对象都会调用该方法
    public String toJson() throws JsonProcessingException {
        //this表示当前对象
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
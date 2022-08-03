package test;

import lombok.Data;

@Data
public class Animals {
    private String name;
    private Integer age;

    private String clz;
    public Animals(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

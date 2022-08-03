package test;

import lombok.Data;

@Data
public class Animals {
    private String name;
    private Integer age;

    public Animals(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

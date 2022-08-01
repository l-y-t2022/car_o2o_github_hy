package cn.wolfcode.domain;

import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor

public class Permission {
    /** */
    private Long id;

    /** */
    private String name;

    /** */
    private String expression;

    public Permission(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    public Permission() {
    }

    //去重
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return expression.equals(that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
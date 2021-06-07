package xyz.kail.demo.mybatis.spring.boot.model.mybatis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserVO {

    private Long id;

    private String name;

    private Integer age;

    public UserVO(Long id) {
        this.id = id;
    }

    public UserVO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}

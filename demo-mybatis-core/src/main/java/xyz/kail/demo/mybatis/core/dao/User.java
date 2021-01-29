package xyz.kail.demo.mybatis.core.dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User  {

    private Long id;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 地址
     */
    private String address;

}
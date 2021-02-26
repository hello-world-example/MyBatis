package xyz.kail.demo.mybatis.spring.boot.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysConfigVO {

    private String variable;

    private String value;

    private Date setTime;

    private String setBy;
}
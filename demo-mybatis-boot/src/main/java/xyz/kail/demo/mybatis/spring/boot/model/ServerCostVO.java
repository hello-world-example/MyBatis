package xyz.kail.demo.mybatis.spring.boot.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerCostVO {

    private String costName;

    private Double costValue;

    private Date lastUpdate;

    private String comment;
}
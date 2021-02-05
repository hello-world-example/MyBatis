package xyz.kail.demo.mybatis.spring.boot.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * <p>
 * Stored Procedures
 * </p>
 *
 * @author Kail
 * @since 2021-02-03
 */
@Getter
@Setter
public class ProcVO {

    private String db;


    private String name;


    private String type;


    private String specificName;


    private String language;


    private String sqlDataAccess;


    private String isDeterministic;


    private String securityType;


    private Blob paramList;


    private Blob returns;


    private Blob body;


    private String definer;


    private LocalDateTime created;


    private LocalDateTime modified;


    private String sqlMode;


    private String comment;


    private String characterSetClient;


    private String collationConnection;


    private String dbCollation;

    @Override
    public String toString() {
        return "ProcVO{" +
                "db='" + db + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", specificName='" + specificName + '\'' +
                ", language='" + language + '\'' +
                ", sqlDataAccess='" + sqlDataAccess + '\'' +
                ", isDeterministic='" + isDeterministic + '\'' +
                ", securityType='" + securityType + '\'' +
                ", paramList=" + paramList +
                ", returns=" + returns +
                ", body=" + body +
                ", definer='" + definer + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", sqlMode='" + sqlMode + '\'' +
                ", comment='" + comment + '\'' +
                ", characterSetClient='" + characterSetClient + '\'' +
                ", collationConnection='" + collationConnection + '\'' +
//                ", dbCollation='" + dbCollation + '\'' +
                '}';
    }
}

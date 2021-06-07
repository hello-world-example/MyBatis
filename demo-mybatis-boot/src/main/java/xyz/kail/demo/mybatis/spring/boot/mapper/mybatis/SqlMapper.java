package xyz.kail.demo.mybatis.spring.boot.mapper.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 执行原生 SQL
 */
@Mapper
public interface SqlMapper {

    @Update("${sql}")
    void update(@Param("sql") String sql);

}

package xyz.kail.demo.mybatis.spring.boot.mapper.sys;

import xyz.kail.demo.mybatis.spring.boot.model.SysConfigVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysConfigMapper {

    Integer countAll();
}
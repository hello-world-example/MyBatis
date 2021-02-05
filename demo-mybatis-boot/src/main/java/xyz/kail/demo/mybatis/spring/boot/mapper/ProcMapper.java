package xyz.kail.demo.mybatis.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.kail.demo.mybatis.spring.boot.model.ProcVO;

import java.util.List;

@Mapper
public interface ProcMapper {

    @Select("select count(*) from proc")
    Integer count();

    @Select("select * from proc")
    List<ProcVO> selectAll();

}

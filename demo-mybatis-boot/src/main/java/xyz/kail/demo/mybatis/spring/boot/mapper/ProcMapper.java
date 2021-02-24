package xyz.kail.demo.mybatis.spring.boot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.kail.demo.mybatis.spring.boot.model.ProcVO;

import java.util.List;

@Mapper
public interface ProcMapper {

    Integer count();

    /**
     * 查不到数据时：返回空列表
     */
    @Select("select * from proc wher4 where db=123")
    List<ProcVO> selectEmptyAll();

    /**
     * 查不到数据时：返回 null
     */
    @Select("select * from proc wher4 where db=123")
    ProcVO selectEmptyOne();


}

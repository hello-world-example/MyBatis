package xyz.kail.demo.mybatis.core.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HelpKeyWordMapper {

    @Results(value = {
            @Result(column = "help_keyword_id", property = "helpKeywordId")
    })
    @Select("SELECT help_keyword_id,name FROM help_keyword")
    List<HelpKeywordVO> selectAll();

}
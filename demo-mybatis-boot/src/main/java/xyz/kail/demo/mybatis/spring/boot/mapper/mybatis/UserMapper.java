package xyz.kail.demo.mybatis.spring.boot.mapper.mybatis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;
import xyz.kail.demo.mybatis.spring.boot.model.mybatis.UserVO;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 初始化表
     */
    void dropTable();

    void initTable();

    @Select("select count(*) from user")
    Integer count();


    /**
     * 批量插入
     */
    Integer insertBatch(@Param("users") Collection<UserVO> users);

    List<UserVO> selectBatch(@Param("userIds") Collection<Long> userIds);

    @Update("<script>" +
            "update user " +
            "   <set>" +
            "       <if test='user.name != null'>name = #{user.name},</if>" +
            "   </set>" +
            "where id = #{user.id}" +
            "</script>" +
            "")
    Integer updateScript(@Param("user") UserVO user);

    List<UserVO> selectWhere(@Param("user") UserVO user);

    Integer updateSet(@Param("user") UserVO user);


    List<UserVO> selectTrim(@Param("user") UserVO user);

    Integer updateTrim(@Param("user") UserVO user);

    @SelectProvider(UserSqlBuilder.class)
    List<UserVO> selectUsers(@Param("user") UserVO user);

    class UserSqlBuilder implements ProviderMethodResolver {

        public static String selectUsers(@Param("user") final UserVO user) {
            final String sql = new SQL() {{
                SELECT("*");
                FROM("user");
                if (null != user && user.getName() != null) {
                    WHERE("name = #{user.name}");
                }
                ORDER_BY("id");
            }}.toString();
            System.out.println(sql);
            return sql;
        }
    }


}

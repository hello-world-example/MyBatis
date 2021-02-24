package xyz.kail.demo.mybatis.spring.boot.mapper;

import xyz.kail.demo.mybatis.spring.boot.model.ServerCostVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServerCostMapper {


    ServerCostVO selectByPrimaryKey(String costName);

    /**
     * 查询的列全都为 null时：
     * 默认false：null，这时集合里面的元素是 null
     * true，空对象，这时集合里面的元素是 对象，但是字段都是 null
     *
     * @see org.apache.ibatis.session.Configuration#setReturnInstanceForEmptyRow
     */
    List<ServerCostVO> selectNullFieldRow();

    /**
     * 查询的列全都为 null时：
     * <p>
     * 默认false：null，这时集合里面的元素是 null
     * true，空对象，这时集合里面的元素是 对象，但是字段都是 null
     */
    ServerCostVO selectNullFieldOne();

    /**
     * 查的是 null，返回的也是 null
     */
    String selectNullFieldSingle();

}
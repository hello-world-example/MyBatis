package xyz.kail.demo.mybatis.spring.boot.conf;

import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MyBatisConf {

    @Component
    public static class Conf implements ConfigurationCustomizer {

        @Override
        public void customize(org.apache.ibatis.session.Configuration configuration) {
            // 驼峰映射
            configuration.setMapUnderscoreToCamelCase(true);

            // 发现自动映射目标未知列的行为
            configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.FAILING);

            // 缓存机制
            configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);

            // 行的所有列都是空时，MyBatis默认返回 null
            // 这里应该设置为 true，因为能查到数据，只是数据列为 null，不能因为字段没值就认为当前数据不存在
            // 根据 ID 查询特定的一些字段的时候很容易出现这些字段都是 null，但是这行数据还是存在的
            configuration.setReturnInstanceForEmptyRow(true);
            // 当数据库查到列为 null 时，是否应该调用 VO 的 set 方法
            // 这里设置为应该，因为 null 也是一种数据状态，同时也保证 VO 中的对象都是包装类型对象，不能是 基本数据类型
            configuration.setCallSettersOnNulls(true);
        }
    }
}

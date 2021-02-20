package xyz.kail.demo.mybatis.core;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import xyz.kail.demo.mybatis.core.datasource.DataSourceFactory;
import xyz.kail.demo.mybatis.core.mapper.HelpKeyWordMapper;
import xyz.kail.demo.mybatis.core.mapper.HelpKeywordVO;

import javax.sql.DataSource;
import java.util.List;

/**
 * 零配置 ORM
 */
public class CodeMain {

    public static void main(String[] args) {
        final DataSource mySqlDatasource = DataSourceFactory.getMySqlDatasource();
        /*
         * 事务与数据源
         */
        final TransactionFactory transactionFactory = new JdbcTransactionFactory();
        final Environment mysql = new Environment("mysql", transactionFactory, mySqlDatasource);

        /*
         * 配置： 拦截器、资源、Cache、属性、代理 等
         */
        final Configuration configuration = new Configuration(mysql);

        // 字段映射进行驼峰转换
        // @Results(value = { @Result(column = "help_keyword_id", property = "helpKeywordId") })
        configuration.setMapUnderscoreToCamelCase(true);
        // 指定 MyBatis 所用日志的具体实现，未指定时将自动查找
        configuration.setLogImpl(Slf4jImpl.class);
        // 【注意】查不到数据时返回一个空实例，集合和实例的区别
        configuration.setReturnInstanceForEmptyRow(false);

        // 手动注册 DAO
        configuration.addMapper(HelpKeyWordMapper.class);

        /*
         * 根据配置创建全局 Session 工厂
         */
        final SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        /*
         * 一个 Session 链接
         */
        try (final SqlSession connection = sessionFactory.openSession()) {
            final HelpKeyWordMapper keyWordDAO = connection.getMapper(HelpKeyWordMapper.class);

            final List<HelpKeywordVO> helpKeywords = keyWordDAO.selectAll();

            helpKeywords.forEach(it -> System.out.printf("%s %s %n", it.getHelpKeywordId(), it.getName()));

        }

    }

}

package xyz.kail.demo.mybatis.core.datasource;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {


    private static DataSource MYSQL_DATASOURCE;

    public static synchronized DataSource getMySqlDatasource() {
        if (null != MYSQL_DATASOURCE) {
            return MYSQL_DATASOURCE;
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://:3307/mysql?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setValidationQuery("SELECT 1");

        MYSQL_DATASOURCE = dataSource;
        return MYSQL_DATASOURCE;
    }

}

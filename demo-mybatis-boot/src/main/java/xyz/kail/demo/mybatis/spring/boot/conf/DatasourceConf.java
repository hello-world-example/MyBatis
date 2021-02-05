package xyz.kail.demo.mybatis.spring.boot.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConf {

    @Bean
    public DataSource mysqlDataSource() {
        final DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://:3307/mysql?useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

}

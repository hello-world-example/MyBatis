package org.kail.demo.mybatis.spring.boot.autoconfigure.support;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

import static org.kail.demo.mybatis.spring.boot.autoconfigure.MyBatisMultiDataSourceProcessorConfigure.MAPPING_BEAN_NAME;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MyBatisSqlSessionFactoryInit implements InitializingBean {

    @Resource
    private DefaultListableBeanFactory beanFactory;

    private final MybatisAutoConfiguration delegation;

    public MyBatisSqlSessionFactoryInit(MybatisProperties properties,
                                        ObjectProvider<Interceptor[]> interceptorsProvider,
                                        ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                        ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                        ResourceLoader resourceLoader,
                                        ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                        ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        this.delegation = new MybatisAutoConfiguration(
                properties,
                interceptorsProvider,
                typeHandlersProvider,
                languageDriversProvider,
                resourceLoader,
                databaseIdProvider,
                configurationCustomizersProvider
        );
        delegation.afterPropertiesSet();
    }


    @Override
    @SuppressWarnings("all")
    public void afterPropertiesSet() throws Exception {
        Map<String, String> mappings = beanFactory.getBean(MAPPING_BEAN_NAME, Map.class);
        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            DataSource dataSource = beanFactory.getBean(entry.getKey(), DataSource.class);
            SqlSessionFactory sqlSessionFactory = delegation.sqlSessionFactory(dataSource);
            beanFactory.registerSingleton(SqlSessionFactory.class.getSimpleName() + "." + entry.getValue(), sqlSessionFactory);
        }
    }


}
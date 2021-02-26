package xyz.kail.demo.mybatis.spring.boot.conf;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;
import xyz.kail.demo.mybatis.spring.boot.conf.support.MyBatisBeanFactoryPostProcessor;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
@Import(MyBatisBeanFactoryPostProcessor.class)
public class MultiDatasourceConfigure implements InitializingBean {

    String[] prefixs = new String[]{"jade.dataSource.", "mybatis.dataSource."};

    @Resource
    private DefaultListableBeanFactory beanFactory;

    private final MybatisAutoConfiguration delegation;

    public MultiDatasourceConfigure(MybatisProperties properties,
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
    public void afterPropertiesSet() throws Exception {
        String[] beanNamesForType = beanFactory.getBeanNamesForType(DataSource.class);

        for (String beanName : beanNamesForType) {
            for (String prefix : prefixs) {
                if (beanName.startsWith(prefix)) {
                    String packageName = beanName.substring(prefix.length());
                    DataSource dataSource = beanFactory.getBean(beanName, DataSource.class);
                    SqlSessionFactory sqlSessionFactory = delegation.sqlSessionFactory(dataSource);

                    beanFactory.registerSingleton("SqlSessionFactory." + packageName, sqlSessionFactory);
                    break;
                }

            }
        }
    }


}

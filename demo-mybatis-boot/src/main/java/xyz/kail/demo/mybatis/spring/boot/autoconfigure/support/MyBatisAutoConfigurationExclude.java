package xyz.kail.demo.mybatis.spring.boot.autoconfigure.support;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationImportFilter;
import org.springframework.boot.autoconfigure.AutoConfigurationMetadata;

/**
 * @see org.springframework.boot.autoconfigure.condition.SpringBootCondition
 */
public class MyBatisAutoConfigurationExclude implements AutoConfigurationImportFilter {

    @Override
    public boolean[] match(String[] autoConfigurationClasses, AutoConfigurationMetadata autoConfigurationMetadata) {
        boolean[] matches = new boolean[autoConfigurationClasses.length];
        for (int i = 0; i < autoConfigurationClasses.length; i++) {
            matches[i] = !MybatisAutoConfiguration.class.getName().equals(autoConfigurationClasses[i]);
        }
        return matches;
    }
}

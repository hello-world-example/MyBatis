package xyz.kail.demo.mybatis.spring.boot.conf;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MybatisConf {

    @Component
    public static class Conf implements ConfigurationCustomizer {

        @Override
        public void customize(org.apache.ibatis.session.Configuration configuration) {
            configuration.setMapUnderscoreToCamelCase(true);
        }
    }
}

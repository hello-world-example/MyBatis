package org.kail.demo.mybatis.spring.boot.autoconfigure.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 把 MyBatisSqlSessionFactoryInit 的初始化时机提前，先进行初始化，
 *
 * @see AbstractApplicationContext#refresh()
 * @see AbstractApplicationContext#onRefresh() 在 onRefresh 阶段初始化
 */
public class MyBatisSqlSessionFactoryServletInitializer implements ServletContextInitializer, ApplicationContextAware, PriorityOrdered {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 初始化 Bean
        applicationContext.getBean(MyBatisSqlSessionFactoryInit.class);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}

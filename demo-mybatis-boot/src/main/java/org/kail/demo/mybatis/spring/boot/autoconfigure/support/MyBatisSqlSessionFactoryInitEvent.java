package org.kail.demo.mybatis.spring.boot.autoconfigure.support;

import org.springframework.context.ApplicationEvent;

public class MyBatisSqlSessionFactoryInitEvent extends ApplicationEvent {

    public MyBatisSqlSessionFactoryInitEvent(Object source) {
        super(source);
    }
}

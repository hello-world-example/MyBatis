package xyz.kail.demo.mybatis.spring.boot.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xyz.kail.demo.mybatis.spring.boot.mapper.ProcMapper;

import javax.annotation.Resource;

@Component
public class DaoRunner implements ApplicationRunner {

    @Resource
    private ProcMapper procMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(procMapper.count());

        System.out.println(procMapper.selectAll());
    }
}

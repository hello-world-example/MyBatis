package xyz.kail.demo.mybatis.spring.boot.runner;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.kail.demo.mybatis.spring.boot.mapper.mysql.ProcMapper;
import xyz.kail.demo.mybatis.spring.boot.mapper.mysql.ServerCostMapper;
import xyz.kail.demo.mybatis.spring.boot.mapper.sys.SysConfigMapper;
import xyz.kail.demo.mybatis.spring.boot.service.RunnerService;

import javax.annotation.Resource;

@Component
public class DaoRunner implements ApplicationRunner {

    @Resource
    private RunnerService runnerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        runnerService.run();
    }
}

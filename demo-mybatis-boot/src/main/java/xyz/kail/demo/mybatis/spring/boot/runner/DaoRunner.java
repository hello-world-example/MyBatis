package xyz.kail.demo.mybatis.spring.boot.runner;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import xyz.kail.demo.mybatis.spring.boot.mapper.mysql.ProcMapper;
import xyz.kail.demo.mybatis.spring.boot.mapper.mysql.ServerCostMapper;
import xyz.kail.demo.mybatis.spring.boot.mapper.sys.SysConfigMapper;

import javax.annotation.Resource;

@Component
public class DaoRunner implements ApplicationRunner {

    @Resource
    private ProcMapper procMapper;

    @Resource
    private ServerCostMapper serverCostMapper;

    @Resource
    private SysConfigMapper sysConfigMapper;

    @Override
    @Transactional("sysTransactionManager")
    public void run(ApplicationArguments args) throws Exception {

        //        System.out.println(procMapper.count());

        //        System.out.println(procMapper.selectEmptyAll());

        //        System.out.println(procMapper.selectEmptyOne());

//        System.out.println(serverCostMapper.selectNullFieldRow());

        System.out.println(serverCostMapper.selectNullFieldOne());

//        System.out.println(serverCostMapper.selectNullFieldSingle());

        System.out.println(sysConfigMapper.countAll());

    }
}

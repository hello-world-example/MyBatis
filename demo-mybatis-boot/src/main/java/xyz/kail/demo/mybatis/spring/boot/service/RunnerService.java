package xyz.kail.demo.mybatis.spring.boot.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.kail.demo.mybatis.spring.boot.mapper.mysql.ProcMapper;
import xyz.kail.demo.mybatis.spring.boot.mapper.mysql.ServerCostMapper;
import xyz.kail.demo.mybatis.spring.boot.mapper.sys.SysConfigMapper;

import javax.annotation.Resource;

@Service
public class RunnerService {

    @Resource
    private ProcMapper procMapper;

    @Resource
    private ServerCostMapper serverCostMapper;

    @Resource
    private SysConfigMapper sysConfigMapper;

//    @Transactional("sysTransactionManager")
    public void run() throws Exception {

        //        System.out.println(procMapper.count());

        //        System.out.println(procMapper.selectEmptyAll());

        //        System.out.println(procMapper.selectEmptyOne());

//        System.out.println(serverCostMapper.selectNullFieldRow());

        System.out.println(serverCostMapper.selectNullFieldOne());

//        System.out.println(serverCostMapper.selectNullFieldSingle());

        System.out.println(sysConfigMapper.countAll());

        //
        System.out.println(serverCostMapper.selectNullFieldOne());

    }
}

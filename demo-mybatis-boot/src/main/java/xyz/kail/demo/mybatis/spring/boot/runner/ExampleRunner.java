package xyz.kail.demo.mybatis.spring.boot.runner;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import xyz.kail.demo.mybatis.spring.boot.mapper.mybatis.UserMapper;
import xyz.kail.demo.mybatis.spring.boot.model.mybatis.UserVO;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class ExampleRunner implements ApplicationRunner {

    @Resource
    private UserMapper userMapper;

    @Resource(name = "SqlSessionFactory.xyz.kail.demo.mybatis.spring.boot.mapper.mybatis")
    private SqlSessionFactory sessionFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userMapper.dropTable();
        userMapper.initTable();

        // 批量插入
        this.batchInsert();
        // 批量查询
        this.selectBatch();
        // 更新用户
        this.updateScript();
        // Where 查询
        this.selectWhere();
        // set 标签
        this.updateSet();
        // trim
        this.trim();
        // sqlSession
        this.sqlSession();
        // sqlProvider
        this.sqlProvider();


        userMapper.dropTable();
    }

    /**
     * 批量插入
     */
    private void batchInsert() {
        final List<UserVO> users = Arrays.asList(
                new UserVO("111", 1),
                new UserVO("222", 2),
                new UserVO("333", 3)
        );

        log.info("before batchInsert: {}", users);
        final Integer insertBatchCount = userMapper.insertBatch(users);
        log.info("after batchInsert: {}", users);
        log.info("count batchInsert {}", insertBatchCount);
        log.info("count user {}", userMapper.count());
    }

    private void selectBatch() {
        final List<UserVO> users = userMapper.selectBatch(Arrays.asList(1L, 2L, 3L));
        log.info("batchSelect {}", users);
    }

    private void updateScript() {
        final UserVO userVO = new UserVO("new", 111);
        userVO.setId(1L);

        userMapper.updateScript(userVO);

        final List<UserVO> users = userMapper.selectBatch(Collections.singleton(1L));

        log.info("updateScript user1: {}", users);
    }

    private void selectWhere() {
        final List<UserVO> users = userMapper.selectWhere(null);

        log.info("selectWhere users: {}", users);
    }

    private void updateSet() {
        final UserVO user = new UserVO(2L);
        user.setAge(222);
        userMapper.updateSet(user);

        final List<UserVO> users = userMapper.selectWhere(null);
        log.info("updateSet users: {}", users);
    }

    private void trim() {
        final UserVO user = new UserVO(3L);
        user.setAge(333);
        userMapper.updateTrim(user);

        final List<UserVO> users = userMapper.selectTrim(user);
        log.info("trim users: {}", users);
    }

    private void sqlSession() {
        try (final SqlSession sqlSession = sessionFactory.openSession()) {
            final DefaultResultHandler defaultResultHandler = new DefaultResultHandler();
            sqlSession.select(UserMapper.class.getName() + ".count", defaultResultHandler);
            log.info("sqlSession.defaultResultHandler: {}", defaultResultHandler.getResultList());
        }
    }

    private void sqlProvider() {
        final UserVO user = new UserVO(3L);
        final List<UserVO> users = userMapper.selectUsers(user);
        log.info("sqlProvider users: {}", users);
    }
}

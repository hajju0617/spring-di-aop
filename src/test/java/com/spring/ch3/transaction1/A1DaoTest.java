package com.spring.ch3.transaction1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class A1DaoTest {
    @Autowired
    A1Dao a1Dao;
    @Autowired
    DataSource dataSource;

    @Test
    public void insertTest() throws Exception {
        // 트랜잭션 매니저 생성.
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        // Default로 트랜잭션 속성이 세팅됨.
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());
        // 트랜잭션 시작.
        try {
            a1Dao.deleteAll();
            a1Dao.insert(1, 100);
            a1Dao.insert(1, 200);

            // 성공시 커밋.
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            // 실패시 롤백.
            platformTransactionManager.rollback(transactionStatus);
        } finally {
        }
    }
}
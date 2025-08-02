package com.spring.ch3;

import com.spring.ch3.transaction.A1Dao;
import com.spring.ch3.transaction.B1Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@Service
public class TxService {
    @Autowired
    A1Dao a1Dao;
    @Autowired
    B1Dao b1Dao;
    @Autowired
    DataSource dataSource;

    public void insertA1WithoutTx() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(1, 200);
    }


//    @Transactional                                    // RuntimeException, Error만 롤백 처리.
    @Transactional(rollbackFor = Exception.class)       // Exception을 롤백 처리.
    public void insertA1WithTxFail() throws Exception {
        a1Dao.insert(1, 100);
//        throw new RuntimeException();
//        throw new Exception();
        a1Dao.insert(1, 200);
    }
    @Transactional
    public void insertA1WithTxSuccess() throws Exception {
        a1Dao.insert(1, 100);
        a1Dao.insert(2, 200);
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public void insertA1WithTx() throws Exception {
//        a1Dao.insert(1, 100);       // 성공
//        insertB1WithTx();
//        a1Dao.insert(2, 100);       // 성공
//    }
//    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//    public void insertB1WithTx() throws Exception {
//        b1Dao.insert(1, 100);       // 성공
//        b1Dao.insert(1, 200);       // 실패
//    }
    public void insertA1WithTx() throws Exception {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Tx 시작
        try {
            a1Dao.insert(1, 100);       // 성공
            insertB1WithTx();
            a1Dao.insert(2, 200);       // 성공
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(transactionStatus);
        } finally {
        }
    }
    public void insertB1WithTx() throws Exception {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);

        // Tx 시작
        try {
            b1Dao.insert(1, 100);       // 성공
            b1Dao.insert(1, 200);       // 실패
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(transactionStatus);
        } finally {
        }
    }
}

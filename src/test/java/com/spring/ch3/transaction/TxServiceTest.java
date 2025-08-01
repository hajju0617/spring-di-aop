package com.spring.ch3.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class TxServiceTest {
    @Autowired
    TxService txService;

    @Test
    public void insertA1WithoutTx() throws Exception {
        txService.insertA1WithoutTx();
    }

    @Test
    public void insertA1WithTxFail() throws Exception {
        txService.insertA1WithTxFail();
    }

    @Test
    public void insertA1WithTxSuccess() throws Exception {
        txService.insertA1WithTxSuccess();
    }
}
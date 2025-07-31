package com.spring.ch3.dao;

import com.spring.ch3.db.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDaoImplTest {
    @Autowired
    UserDao userDao;

    @Test
    public void deleteUser() {
    }

    @Test
    public void selectUser() {
    }

    @Test
    public void insertUser() {
    }

    @Test
    public void updateUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2021, 1, 1);

        userDao.deleteUser("asdf");     // 기존에 INSERT한 값 지우기.
        User user = new User("asdf", "1234", "가나다", "qwe@naver.com", new Date(calendar.getTimeInMillis()), "naver", new Date());
        int rowCount = userDao.insertUser(user);
        assertTrue(rowCount == 1);

        user.setPwd("4321");
        user.setEmail("ewq@naver.com");
        rowCount = userDao.updateUser(user);
        assertTrue(rowCount == 1);      // 업데이트가 수행 됐는지 확인.

        User user2 = userDao.selectUser(user.getId());
        System.out.println("user = " + user);
        System.out.println("user2 = " + user2);
        assertTrue(user.equals(user2));         // 업데이트 한게 일치하는 지 확인.
    }

    @Test
    public void deleteAll() {
    }
}
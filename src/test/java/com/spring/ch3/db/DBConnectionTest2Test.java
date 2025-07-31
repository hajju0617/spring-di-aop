package com.spring.ch3.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired      // 자동 주입.
    DataSource dataSource;

    @Test
    public void selectUserTest() throws Exception {
        deleteAll();    // user_info 테이블에 모든 데이터를 지우는 메서드.
        User user = new User("zxcv", "1234", "홍길동", "abc@naver.com", new Date(), "naver", new Date());
        int rowCount = insertUser(user);
        User user2 = selectUser("zxcv");
        assertTrue(user2.getId().equals("zxcv"));
    }

    public User selectUser(String id) throws Exception{
        // DataSource로 부터 데이터베이스 연결을 가져옴.
        Connection connection = dataSource.getConnection();

        String sql = "select * from user_info where id = ?";
        // PreparedStatement : statement에 비해 SQL 인젝션 공격과 성능향상의 장점을 갖고 있음.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();     // executeQuery : SELECT

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getString(1));
            user.setPwd(resultSet.getString(2));
            user.setName(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setBirth(new Date(resultSet.getDate(5).getTime()));
            user.setSns(resultSet.getString(6));
            user.setReg_date(new Date(resultSet.getDate(7).getTime()));
            return user;
        }
        return null;        // select 결과가 없으면 null을 반환.
    }

    @Test   // insertUser를 테스트 하는 메서드.
    public void insertUesrTest() throws Exception{
        User user = new User("zxcv", "1234", "홍길동", "abc@naver.com", new Date(), "naver", new Date());

        deleteAll();    // user_info 테이블에 모든 데이터를 지우는 메서드.

        int rowCount = insertUser(user);
        assertTrue(rowCount == 1);
    }

    private void deleteAll() throws Exception {
        // DataSource로 부터 데이터베이스 연결을 가져옴.
        Connection connection = dataSource.getConnection();

        String sql = "delete from user_info";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }

    // 사용자 정보를 user_info 테이블에 저장하는 메서드.
    public int insertUser(User user) throws Exception {
        // DataSource로 부터 데이터베이스 연결을 가져옴.
        Connection connection = dataSource.getConnection();

        String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";
        // PreparedStatement : statement에 비해 SQL 인젝션 공격과 성능향상의 장점을 갖고 있음.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getPwd());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        preparedStatement.setString(6, user.getSns());

        int rowCount = preparedStatement.executeUpdate();
        return rowCount;
    }

    @Test
    public void deleteUserTest() throws Exception {
        // delete
        deleteAll();
        int rowCount = deleteUser("zxcv");
        assertTrue(rowCount == 0);

        // insert
        User user = new User("zxcv", "1234", "홍길동", "abc@naver.com", new Date(), "naver", new Date());
        rowCount = insertUser(user);
        assertTrue(rowCount == 1);

        // delete
        rowCount = deleteUser(user.getId());
        assertTrue(rowCount == 1);

        // select
        assertTrue(selectUser(user.getId()) == null);
    }

    public int deleteUser(String id) throws Exception {
        // DataSource로 부터 데이터베이스 연결을 가져옴.
        Connection connection = dataSource.getConnection();

        String sql = "delete from user_info where id = ?";
        // PreparedStatement : statement에 비해 SQL 인젝션 공격과 성능향상의 장점을 갖고 있음.
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
        int rowCount = preparedStatement.executeUpdate();
        return rowCount;
    }
    @Test
    public void updateUserTest() throws Exception {
        // delete
        deleteAll();
        // insert
        User user = new User("zxcv", "1234", "홍길동", "abc@naver.com", new Date(), "naver", new Date());
        int rowCount = insertUser(user);
        assertTrue(rowCount == 1);

        user.setName("홍길동2");
        int rowCount2 = updateUser(user);
        assertTrue(rowCount2 == 1);
        assertTrue(user.getName().equals("홍길동2"));
    }

    // 매개변수로 받은 사용자 정보로 user_info 테이블을 UPDATE하는 메서드.
    public int updateUser(User user) throws Exception {
        // DataSource로 부터 데이터베이스 연결을 가져옴.
        Connection connection = dataSource.getConnection();

        String sql = "update user_info set name = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getId());
        int rowCount = preparedStatement.executeUpdate();
        return rowCount;
    }


    @Test       // @Test 에노테이션 붙여주기.
    public void springJdbcConnectionTest() throws Exception{
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = dataSource.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null);     // 괄호 안의 조건식이 true면 테스트 성공.
    }

    @Test
    public void transactionTest() throws Exception {
        Connection connection = null;
        try {
            deleteAll();
            // DataSource로 부터 데이터베이스 연결을 가져옴.
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);

            String sql = "insert into user_info values (?, ?, ?, ?, ?, ?, now())";
            // PreparedStatement : statement에 비해 SQL 인젝션 공격과 성능향상의 장점을 갖고 있음.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "qwer");
            preparedStatement.setString(2, "1234");
            preparedStatement.setString(3, "abc");
            preparedStatement.setString(4, "abc@naver.com");
            preparedStatement.setDate(5, new java.sql.Date(new Date().getTime()));
            preparedStatement.setString(6, "naver");

            int rowCount = preparedStatement.executeUpdate();       // INSERT, DELETE, UPDATE

            preparedStatement.setString(1, "qwer");
            rowCount = preparedStatement.executeUpdate();

            connection.commit();            // 커밋.
        } catch (Exception e) {
            connection.rollback();          // 롤백.
            e.printStackTrace();
        } finally {
        }
    }
}
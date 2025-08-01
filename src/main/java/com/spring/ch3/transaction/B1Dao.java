package com.spring.ch3.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class B1Dao {
    @Autowired
    DataSource dataSource;

    public int insert(int key, int value) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
//            connection = dataSource.getConnection();
            connection = DataSourceUtils.getConnection(dataSource);

            // 커넥션이 정말 같은지 확인.
            System.out.println("connection = " + connection);

            preparedStatement = connection.prepareStatement("insert into b1 values(?, ?)");
            preparedStatement.setInt(1, key);
            preparedStatement.setInt(2, value);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외가 발생하면 예외를 던져서 테스트 코드의 try-catch가 받아서 롤백이 실행될 수 있도록 하기 위해서 에러를 던짐.
            throw e;
        } finally {
//            close(connection, preparedStatement);
            close(preparedStatement);
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
    private void close(AutoCloseable... acs) {      // 매개변수로 가변인자 설정.
        for(AutoCloseable ac :acs)      // for문으로 try-catch 문 여러 번 될 수 있도록.
            try { if(ac!=null) ac.close(); } catch(Exception e) { e.printStackTrace(); }
    }

    public void deleteAll() throws Exception {
        // Connection connection = DataSourceUtils.getConnection(dataSource);
        // deleteAll 메서드는 트랜잭션과 별개로 동작해야하므로 위처럼 작성하면 안됨.
        Connection connection = dataSource.getConnection();

        String sql = "delete from a1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        close(preparedStatement);
    }
}

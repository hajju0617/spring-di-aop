package com.spring.ch3.db;

import java.sql.*;

public class DBConnectionTest {
    public static void main(String[] args) throws Exception {
        // 스키마의 이름(springbasic)이 다른 경우 알맞게 변경해야 함.
        String DB_URL = "jdbc:mysql://localhost:3306/springbasic?useUnicode=true&characterEncoding=utf8";

        // DB의 userid와 pwd를 알맞게 변경해야 함.
        String DB_USER = "root";
        String DB_PASSWORD = "zgxfcd!0236";

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // 데이터베이스의 연결을 얻음.
        Statement stmt  = conn.createStatement(); // Statement를 생성.

        String query = "SELECT now()"; // 시스템의 현재 날짜시간을 출력하는 쿼리(query).
        ResultSet rs = stmt.executeQuery(query); // query를 실행한 결과를 rs에 저장.

        // 실행결과가 담긴 rs에서 한 줄씩 읽어서 출력
        while (rs.next()) {
            String curDate = rs.getString(1);  // 읽어온 행의 첫번째 컬럼의 값을 String으로 읽어서 curDate에 저장.
            System.out.println(curDate);
        }
    }
}

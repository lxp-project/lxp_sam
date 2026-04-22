package com.lxp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionManager {

    // 애플리케이션 전체에서 공유할 단 하나의 DataSource 인스턴스
    private static final HikariDataSource dataSource;

    static {
        try {

            Properties props = new Properties();

            // 1. properties 파일 경로 가져오기
            props.load(JdbcConnectionManager.class.getClassLoader().getResourceAsStream("database.properties"));

            // 2. HikariConfig 객체 생성
            HikariConfig config = new HikariConfig(props);

            // 3. DataSource 객체 1회 생성
            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}

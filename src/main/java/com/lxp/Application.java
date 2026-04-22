package com.lxp;

import com.lxp.config.JdbcConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try(Connection connection = JdbcConnectionManager.getConnection()) {
            System.out.println(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

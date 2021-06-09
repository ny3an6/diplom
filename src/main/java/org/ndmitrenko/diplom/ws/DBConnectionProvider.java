package org.ndmitrenko.diplom.ws;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnectionProvider {

    private static final String dbHost = "localhost";
    private static final String username = "postgres";
    private static final String password = "";

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            String connectionString = "jdbc:postgresql://"+dbHost+":5432/analitLogs";

            return DriverManager.getConnection(connectionString, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

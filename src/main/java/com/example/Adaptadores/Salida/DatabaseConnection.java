package com.example.Adaptadores.Salida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://mail.tecnoweb.org.bo/db_grupo03sc";
    private static final String DB_USER = "grupo03sc";
    private static final String DB_PASS = "grup003grup003*";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}

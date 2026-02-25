package org.antonio.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static Dotenv dotenv = Dotenv.load();
  private static final String URL = dotenv.get("DB_URL");
  private static final String USER = dotenv.get("DB_USER");
  private static final String PASS = dotenv.get("DB_PASS");

  // static methods to create connection
  public static Connection getConnection () throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }

  // static method to close connection
  public static void closeConnection (Connection connection) throws SQLException {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

package com.indeed.jenkins.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class JenkinsCredentialTest {

  private static String
    DATABASE_HOST = System.getProperty("databaseHost", "localhost"),
    DATABASE_NAME = System.getProperty("databaseName", "cobraats"),
    USERNAME = System.getProperty("userName", "cobraats"),
    PASSWORD = System.getProperty("passWord", "");

  @Test
  void test() throws SQLException {

    Connection connection = DriverManager.getConnection(
        String.format("jdbc:postgresql://%s:5432/%s", DATABASE_HOST, DATABASE_NAME),
        USERNAME,
        PASSWORD);

      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT column_name FROM information_schema.columns "
          + "WHERE table_name   = 'jobs'");


      Set<String> columns = new LinkedHashSet<String>();
      System.out.print("|");
      while (resultSet.next()) {
        String columnName = resultSet.getString(1);
        System.out.print(columnName + "\t");
        System.out.print("|");

        columns.add(columnName);
      }
      System.out.println();

      resultSet = statement.executeQuery("SELECT * FROM jobs LIMIT 10");
      while (resultSet.next()) {
        System.out.print("|");
        for (String column: columns) {
          String columnValue = resultSet.getString(column);
          System.out.print(columnValue + "\t");
          System.out.print("|");
        }
      }
  }

}

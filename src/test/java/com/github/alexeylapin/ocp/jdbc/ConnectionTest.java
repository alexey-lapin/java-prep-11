package com.github.alexeylapin.ocp.jdbc;

import org.h2.jdbc.JdbcConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionTest {

    // DriverManager
    // Driver
    // Connection
    // PreparedStatement
    // CallableStatement
    // ResultSet

    @Test
    void name() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "s3cret");

        assertThat(connection).isInstanceOf(JdbcConnection.class);
    }

    @Test
    void name2() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");

        String sql = "create table superheroes(id int4, name varchar(100))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        }
    }

    @Test
    void name21() throws Exception {
        var connection = DriverManager.getConnection("jdbc:h2:mem:test");

        var sql = "create table superheroes(id int4, name varchar(100))";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            var i = preparedStatement.executeUpdate();
            System.out.println(i);
        }
    }

    @Test
    void name3() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");

        String sql = "create table superheroes(id int4, name varchar(100))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        }

        String sql2 = "insert into superheroes values(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Hulk");
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        }
    }

}

package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUsers {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "avto4";
    private final String LOGIN = "root"; // Если OpenServer, то здесь mysql напишите
    private final String PASS = ""; // Если OpenServer, то здесь mysql напишите


    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public int getUser(String log, String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT role_idrole FROM staff WHERE staffPas = ? AND staffLog = ? UNION " +
                "SELECT role_idrole FROM clients WHERE ClientsLogin = ? AND ClientsPassword = ?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, pass);
        statement.setString(2, log);
        statement.setString(3, log);
        statement.setString(4, pass);
        ResultSet res = statement.executeQuery();


        int role = 0;
        while (res.next()) {

            role = res.getInt("role_idrole");
        }
        return role;

    }

    public int getIdClient(String login) throws SQLException, ClassNotFoundException {
        // Используем параметризованный SQL-запрос без кавычек
        String sql = "SELECT idClients FROM clients WHERE ClientsLogin = ? ";

        // Создаем подготовленное выражение
        PreparedStatement prst = getDbConnection().prepareStatement(sql);
        prst.setString(1, login);

        // Выполняем запрос и получаем результирующий набор данных
        ResultSet resultSet = prst.executeQuery();

        int clientId = 0;

        // Извлекаем значение idClient из результирующего набора
        if (resultSet.next()) {
            clientId = resultSet.getInt("idClients");
        }

        // Возвращаем значение idClient
        return clientId;
    }


    public Boolean getPass(String pass) throws SQLException, ClassNotFoundException {
        String sql = "SELECT ClientsPassword FROM clients WHERE ClientsPassword = ? "
                + "UNION ALL "
                + "SELECT staffPas FROM staff WHERE staffPas = ?";
        PreparedStatement prst = getDbConnection().prepareStatement(sql);
        prst.setString(1, pass);
        prst.setString(2, pass);
        ResultSet resultSet = prst.executeQuery();

        if (resultSet.next()) {
            return true; // Пароль найден
        } else {
            return false; // Пароль не найден
        }
    }

    public void insertUser(String name, String surname, String otch, String log, String pass) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO clients VALUES (null,?,?,?,?,?,3)";

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, name);
        prSt.setString(2, surname);
        prSt.setString(3, otch);
        prSt.setString(4, log);
        prSt.setString(5, pass);

        prSt.executeUpdate();
    }
}


package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class ClientProfile {
    DBUsers dbUsers = null;
    @FXML
    private ListView<String> spis_zak_act;
    @FXML
    private ImageView img_exit;


    public void initialize() {
                Authorization authorization = new Authorization();
                int role = authorization.b;
                // Подключение к базе данных
                dbUsers = new DBUsers();
                String url = "jdbc:mysql://localhost:3306/exampledb";
                String username = "exampleuser";
                String password = "examplepassword";
                try {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    // Выполнение запроса на выборку значений из таблицы "order"
                    String orderQuery = "SELECT * FROM catalogauto_has_clients WHERE clients_idClients = ?";
                    PreparedStatement orderStatement = connection.prepareStatement(orderQuery);

                    // Устанавливаем значение параметра в запросе
                    orderStatement.setInt(1, role);
                    // Выполняем запрос и получаем результат
                    ResultSet orderResultSet = orderStatement.executeQuery();

                    // Итерация по результатам запроса и добавление значений в ListView
                    while (orderResultSet.next()) {
                        String status = orderResultSet.getString("status_order");
                        Date date = orderResultSet.getDate("Date_order");
                        BigDecimal pricefull = orderResultSet.getBigDecimal("price_order");
                        spis_zak_act.getItems().add(String.valueOf("Cтатус заказа: "
                                + " " + status + " " + "Дата заказа: "
                                + " " + date + " " + "Стоимость заказа: "
                                + " " + pricefull));
                    }

                    // Закрытие ресурсов
                    orderResultSet.close();
                    orderStatement.close();

                    // Закрытие соединения
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    @FXML
    private void handleImagVixod(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 954, 273);
            stage.setResizable(false);
            Stage loginStage = (Stage) img_exit.getScene().getWindow();
            loginStage.close();
            stage.setTitle("Главная страница");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }






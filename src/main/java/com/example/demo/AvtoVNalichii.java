package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AvtoVNalichii {
    @FXML
    private Button zakaz;

    @FXML
    private ListView<AutoData> idspisavto;
    @FXML
    private ImageView exit_img;
    private boolean isUserAuthorized = false;
    @FXML
    private ImageView exit_img_2;


    DB db = null;
    DBUsers dbUsers = null;

    @FXML
    void initialize() {
        // Инициализируем объект базы данных
        db = new DB();
        loadInfo();
    }

    void loadInfo() {
        try {
            List<AutoData> ls = db.getProduct();
            idspisavto.getItems().addAll(ls);
            idspisavto.setCellFactory(stringListView -> {
                ListCell<AutoData> cell = new Autostr();
                ContextMenu contextMenu = new ContextMenu();
                AutoData item = cell.getItem();
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Zakaz(MouseEvent mouseEvent) {
        try {
            dbUsers = new DBUsers();
            Authorization authorization = new Authorization();
            int role = authorization.b;
            System.out.println(role);
            if (role == 0) {
                // Создание всплывающего окна с ошибкой
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка авторизации");
                alert.setHeaderText(null);
                alert.setContentText("Пожалуйста, авторизуйтесь перед оформлением заказа.");
                alert.showAndWait();
                zakaz.setVisible(false);
            } else if (role == 1) {
                zakaz.setVisible(false);
            } else if (role == 2) {
                zakaz.setVisible(false);
            } else if (role == 3) {
                zakaz.setVisible(true);
            } else {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Zakaz_s_dop.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 1413, 552);
                    stage.setResizable(false);
                    Stage loginStage = (Stage) zakaz.getScene().getWindow();
                    loginStage.close();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleImagVixod(MouseEvent event) {
        try {
            dbUsers = new DBUsers();
            Authorization authorization = new Authorization();
            int clientId = authorization.b;
            System.out.println(clientId);
            // Загружаем fxml файл для новой сцены
            if (clientId == 2) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ManagerCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) exit_img_2.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Менеджера");
                stage.setScene(scene);
                stage.show();

            } else if (clientId == 1) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DirectorCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) exit_img_2.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Директора");
                stage.setScene(scene);
                stage.show();
            } else if (clientId == 0) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) exit_img_2.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Главная страница");
                stage.setScene(scene);
                stage.show();

            } else if (clientId == 3) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) exit_img_2.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Пользователя");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Unknown client ID: " + clientId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
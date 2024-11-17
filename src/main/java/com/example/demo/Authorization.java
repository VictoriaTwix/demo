package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Authorization {
    @FXML
    private TextField idLog;

    @FXML
    private PasswordField idPass;

    @FXML
    Button idVhod;

    @FXML
    private Button idVhod1;

    DBUsers db = null;
    public static int b;
    public static int cl;
    public  static  int role;


    @FXML
    void initialize() {

        // Инициируем объект
        db = new DBUsers();

        // Обработчик события. Сработает при нажатии на кнопку
        idVhod.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    // Проверяем является ли поле заполненным
                    if (!idLog.getText().trim().equals("") & !idPass.getText().trim().equals("") & !db.getPass(idPass.getText()) == false) {
                        // Вызываем метод из класса DB
                        // через этот метод будет добавлено новое задание
                        int a = db.getUser(idLog.getText(), idPass.getText());
                        role =  db.getUser(idLog.getText(), idPass.getText());
                        b = db.getIdClient(idLog.getText());
                        System.out.println(b);
                        if (a == 2) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ManagerCabin.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                            stage.setResizable(false);
                            Stage loginStage = (Stage) idVhod.getScene().getWindow();
                            loginStage.close();
                            stage.setTitle("Личный кабинет Менеджера");
                            stage.setScene(scene);
                            stage.show();

                        }
                        if (a == 1) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DirectorCabin.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                            stage.setResizable(false);
                            Stage loginStage = (Stage) idVhod.getScene().getWindow();
                            loginStage.close();
                            stage.setTitle("Личный кабинет Директора");
                            stage.setScene(scene);
                            stage.show();
                        }
                        if (a == 0) {
                            showMessage("Ошибка", "Неверные данные");
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("captcha.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 250, 140);
                            stage.setResizable(false);
                            stage.setTitle("Каптча");
                            stage.setScene(scene);
                            stage.show();
                            Stage loginStage = (Stage) idVhod.getScene().getWindow();
                            loginStage.close();

                        }
                        if (a == 3) {
                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientCabin.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                            stage.setResizable(false);
                            Stage loginStage = (Stage) idVhod.getScene().getWindow();
                            loginStage.close();
                            stage.setTitle("Личный кабинет Пользователя");
                            stage.setScene(scene);
                            stage.show();
                        }
                    } else {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("captcha.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(), 250, 140);
                        stage.setResizable(false);
                        stage.setTitle("Каптча");
                        stage.setScene(scene);
                        stage.show();
                        Stage loginStage = (Stage) idVhod.getScene().getWindow();
                        loginStage.close();
                    }
                } catch (SQLException e) { // Отслеживаем ошибки
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        idVhod1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, что будет срабатывать
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Registration.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 316, 420);
                    stage.setResizable(false);
                    Stage loginStage = (Stage) idVhod.getScene().getWindow();
                    loginStage.close();
                    stage.setTitle("Регистрация");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
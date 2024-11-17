package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Registration {
    @FXML
    private TextField famil_l;

    @FXML
    private TextField login_l;

    @FXML
    private TextField name_l;

    @FXML
    private TextField otch_l;

    @FXML
    private TextField pass_l;

    @FXML
    private Button save_akk;
    @FXML
    private ImageView exit;
    DBUsers db = null;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // Инициируем объект
        db = new DBUsers();
        save_akk.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            // Метод, который будет срабатывать при нажатии на кнопку сохранить
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Проверяем, что все поля, кроме отчества, заполнены
                if (!name_l.getText().trim().equals("") && !famil_l.getText().trim().equals("") && !login_l.getText().trim().equals("") && !pass_l.getText().trim().equals("")) {
                    try {
                        db.insertUser(name_l.getText(), famil_l.getText(), otch_l.getText(), login_l.getText(), pass_l.getText());
                        // Если данные введены правильно, выводим всплывающее окно с сообщением об успешной регистрации
                        showMessage("Успешная регистрация", "Вы успешно зарегистрировались!");
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                        stage.setResizable(false);
                        Stage loginStage = (Stage) save_akk.getScene().getWindow();
                        loginStage.close();
                        stage.setTitle("");
                        stage.setScene(scene);
                        stage.show();
                    } catch (SQLException e) {
                        // Если возникает ошибка при вставке данных в базу, выбрасываем исключение
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        // Если возникает ошибка при загрузке драйвера JDBC, выбрасываем исключение
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Если какое-то поле пустое, выводим всплывающее окно с сообщением об ошибке заполнения полей
                    showMessage("Ошибка", "Не все поля были заполнены");
                }
            }
        });
    }

    // Метод для очищения текстовых полей
    private void clearTextFields() {
        name_l.clear();
        famil_l.clear();
        otch_l.clear();
        login_l.clear();
        pass_l.clear();
    }

    // Метод для отображения всплывающего окна с сообщением
    void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleImagVixodDir(MouseEvent event) {
        try {
            // Загружаем fxml файл для новой сцены
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 954, 273);
            stage.setResizable(false);
            Stage loginStage = (Stage) exit.getScene().getWindow();
            loginStage.close();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Главная страница");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
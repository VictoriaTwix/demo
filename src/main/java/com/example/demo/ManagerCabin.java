package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerCabin {
    @FXML
    private Label auto_v_nalich;

    @FXML
    private Label contact;

    @FXML
    private ImageView img_author;

    @FXML
    private ImageView img_poisk;

    @FXML
    private ImageView logo_cl;

    @FXML
    private Label models_car;

    @FXML
    private ImageView zastavka;

    @FXML
    private void handleImageClicked(MouseEvent event) {
        try {
            // Загружаем fxml файл для новой сцены
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("managerProfile.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 510, 383);
            stage.setResizable(false);
            Stage loginStage = (Stage) img_author.getScene().getWindow();
            loginStage.close();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Личный профиль менеджера");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод, который будет вызван при нажатии на label contact
    @FXML
    public void showContactInfo() {
        String contactInfo = getContactInfo();
        // Если надпись на label уже содержит контактную информацию, то просто возвращаем основное название label
        if (contact.getText().equals(contactInfo)) {
            contact.setText("Контакты");
        } else {
            // Выводим контактную информацию в окне
            contact.setText(contactInfo);
        }
    }

    @FXML
    private String getContactInfo() {
        return "Телефон: +7 999-123-45-67\nEmail: example@example.com";
    }

    @FXML
    private void openAvtoVNalichiiFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Avto_v_nalichii.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 570, 450);
            stage.setResizable(false);
            Stage loginStage = (Stage) auto_v_nalich.getScene().getWindow();
            loginStage.close();
            stage.setTitle("Автомобили в наличии");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Ошибка при открытии fxml файла: " + e.getMessage());
        }
    }

    @FXML
    private void openModelAvtoFXML() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("models_ryad.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 637, 458);
            stage.setResizable(false);
            Stage loginStage = (Stage) models_car.getScene().getWindow();
            loginStage.close();
            stage.setTitle("Модельный ряд автомобилей");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Ошибка при открытии fxml файла: " + e.getMessage());
        }
    }
}
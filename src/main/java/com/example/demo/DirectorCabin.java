package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DirectorCabin {
    @FXML
    private Label auto_v_nalich_dir;

    @FXML
    private Label contact_dir;

    @FXML
    private ImageView img_cabinDir;

    @FXML
    private ImageView logo_cl_dir;

    @FXML
    private Label models_car_dir;

    @FXML
    private void handleImage(MouseEvent event) {
        try {
            // Загружаем fxml файл для новой сцены
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dirProfile.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 492);
            stage.setResizable(false);
            Stage loginStage = (Stage) img_cabinDir.getScene().getWindow();
            loginStage.close();
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Личный кабинет директора");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод, который будет вызван при нажатии на label contact
    @FXML
    public void showContactInfo() {
        String contactInfo = getContactInfo();
        // Если надпись на label уже содержит контактную информацию, то просто возвращаем основное название label
        if (contact_dir.getText().equals(contactInfo)) {
            contact_dir.setText("Контакты");
        } else {
            // Выводим контактную информацию в окне
            contact_dir.setText(contactInfo);
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
            Stage loginStage = (Stage) auto_v_nalich_dir.getScene().getWindow();
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
            Stage loginStage = (Stage) models_car_dir.getScene().getWindow();
            loginStage.close();
            stage.setTitle("Модельный ряд автомобилей");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Ошибка при открытии fxml файла: " + e.getMessage());
        }
    }
}


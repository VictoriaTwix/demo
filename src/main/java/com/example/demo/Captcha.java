package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Captcha {
    private static final int captchaLength = 5;

    @FXML
    private Canvas canvas;
    @FXML
    private TextField textInputField;
    @FXML
    private Button id_proverka;

    private CaptchaGenerator captchaGenerator;
    private String captchaText;


    public boolean validate() {
        if (!Objects.equals(this.captchaText, this.textInputField.getText())) {
            regenerateCaptcha();
            this.textInputField.setText("");
            return false;
        }

        return true;
    }

    @FXML
    private void initialize() {
        this.captchaGenerator = new CaptchaGenerator(this.canvas);

        regenerateCaptcha();
    }

    private void regenerateCaptcha() {
        this.captchaText = this.captchaGenerator.generate(captchaLength);
    }

    public void checkCaptcha(ActionEvent actionEvent) throws IOException {
        if (validate()) {
            Stage currentStage = (Stage) textInputField.getScene().getWindow();
            currentStage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("authorization.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 700, 270);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();
        } else {
            Stage currentStage = (Stage) textInputField.getScene().getWindow();
            currentStage.close();
            showMessage("Ошибка", "Неверная капча авторизация заблокирована");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("authorization.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 720, 320);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Authorization controller = fxmlLoader.getController();
            controller.idVhod.setDisable(true); // блокируем кнопку авторизации
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> controller.idVhod.setDisable(false)); // разблокируем кнопку авторизации через 10 секунд
                }
            }, 10000);

        }
    }

    // Метод для отображения всплывающего окна с сообщением
    void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

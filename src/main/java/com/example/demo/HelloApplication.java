package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 954, 273);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Главная страница");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
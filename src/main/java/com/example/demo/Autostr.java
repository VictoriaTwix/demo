package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Autostr extends ListCell<AutoData> {
    @FXML
    private Label date_a;

    @FXML
    private Label deskr;

    @FXML
    private AnchorPane idContainer;

    @FXML
    private ImageView idImg;

    @FXML
    private VBox idStr;

    @FXML
    private Label marka;

    @FXML
    private Label model;

    @FXML
    private Label price;

    private FXMLLoader mLLoader;
    // Инициализация компонентов FXML-файла
    @FXML
    private void initialize() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("autostr.fxml"));
        loader.setController(this);

        try {
            mLLoader = loader;
            mLLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод updateItem() вызывается для каждого элемента списка.
    @Override
    protected void updateItem(AutoData auto, boolean empty) {
        super.updateItem(auto, empty);

        if (empty || auto == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                initialize(); // Вызываем метод initialize(), чтобы проинициализировать компоненты FXML-файла
            }

            // Устанавливаем значения для компонентов на основе данных автомобиля
            marka.setText(auto.getMarka());
            model.setText(auto.getModelDop());
            price.setText(String.valueOf(auto.getPrice()));
            deskr.setText(auto.getDeskr());
            date_a.setText(auto.getDate_a());

            if (auto.getPhoto() != null && !auto.getPhoto().isEmpty()) {
                File file = new File(auto.getPhoto());
                try {
                    String urlImage = file.toURI().toURL().toString();
                    System.out.println(urlImage);
                    Image image = new Image(urlImage);
                    idImg.setImage(image);
                } catch (MalformedURLException ignored) {
                }
            }

            setText(null);
            setGraphic(idContainer);
        }
    }
}
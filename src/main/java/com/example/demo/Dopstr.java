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

public class Dopstr extends ListCell<DopData> {
    @FXML
    private AnchorPane idContainerDop;

    @FXML
    private VBox idDop;

    @FXML
    private ImageView idDopImg;

    @FXML
    private Label nameDop;

    @FXML
    private Label priceDop;
    private FXMLLoader mLLoader;

    @FXML
    private void initialize() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dopstr.fxml"));
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
    protected void updateItem(DopData dop, boolean empty) {
        super.updateItem(dop, empty);

        if (empty || dop == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                initialize(); // Вызываем метод initialize(), чтобы проинициализировать компоненты FXML-файла
            }

            // Устанавливаем значения для компонентов на основе данных автомобиля
            priceDop.setText(String.valueOf(dop.getPriceDop()));
            nameDop.setText(dop.getNameDop());

            if (dop.getPhotodop() != null && !dop.getPhotodop().isEmpty()) {
                File file = new File(dop.getPhotodop());
                try {
                    String urlImage = file.toURI().toURL().toString();
                    System.out.println(urlImage);
                    Image image = new Image(urlImage);
                    idDopImg.setImage(image);
                } catch (MalformedURLException ignored) {
                }
            }

            setText(null);
            setGraphic(idContainerDop);
        }
    }
}

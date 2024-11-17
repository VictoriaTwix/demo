package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.demo.DB.getConnection;

public class RedactProduct {
    @FXML
    private ImageView add_photo_img;

    @FXML
    private TextField age_text;

    @FXML
    private TextField descr_text;

    @FXML
    private ImageView exit;

    @FXML
    private TextField nalichie;

    @FXML
    private TextField phototext;

    @FXML
    private TextField price_text;

    @FXML
    private Button update_info;
    private AutoData selectedProduct;

    DB db = null;
    @FXML
    public void initialize() {

        add_photo_img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> chooseImage());
    }
    public void setSelectedProduct(AutoData product) {
        selectedProduct = product;
        // Заполняем значениями из выбранного продукта соответствующие текстовые поля
        age_text.setText(selectedProduct.getDate_a());
        descr_text.setText(selectedProduct.getDeskr());
        nalichie.setText(selectedProduct.getNal());
        phototext.setText(selectedProduct.getPhoto());
        price_text.setText(String.valueOf(selectedProduct.getPrice()));
    }

    @FXML
    void updateInfo() {
        // Получаем новые значения из текстовых полей
        String newDateProizvod = age_text.getText();
        String newDescr = descr_text.getText();
        String newStatusNal = nalichie.getText();
        String catalogautoPhoto = getCatalogautoPhoto();
        String newPrice = price_text.getText();

        // Обновляем значения в базе данных
        try {
            db = new DB();
            db.updateProduct(selectedProduct.getIdAvto(), newDateProizvod, newPrice, newStatusNal, newDescr, catalogautoPhoto);
            // Создаем информационное диалоговое окно
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Обновление продукта");
            alert.setHeaderText("Успешное обновление");
            alert.setContentText("Продукт успешно обновлен.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
    public String getCatalogautoPhoto() {
        String catalogautoPhoto = phototext.getText();
        // Получаем фото продукта
        return catalogautoPhoto;
    }
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");

        // Опционально: фильтр для отображения только изображений
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);

        // Показать диалог выбора файла
        File file = fileChooser.showOpenDialog(add_photo_img.getScene().getWindow());

        if (file != null) {
            // Здесь можно бработать выбранный файл и добавить его в текстовое поле
            phototext.setText(file.getName());
        }
    }
}

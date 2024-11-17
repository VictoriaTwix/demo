package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AddCatalogProduct {
    @FXML
    private Button add_button_prod;
    @FXML
    private ImageView add_photo_img;

    @FXML
    private TextField age_text11;

    @FXML
    private TextField descr_text;

    @FXML
    private ComboBox<String> marka_box;

    @FXML
    private ComboBox<String> model_text1;

    @FXML
    private TextField model_text11;

    @FXML
    private ComboBox<String> post_box;

    @FXML
    private TextField price_text;
    @FXML
    private ImageView exit;



    DB db = null;

    @FXML
    public void initialize() {
        try {
            add_button_prod.setOnAction(event -> addProduct());
            add_photo_img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> chooseImage());
            db = new DB();
            List<String> marks = db.getmarka();
            List<String> model = db.getmodel();
            List<String> postav = db.getpostav();
            model_text1.getItems().addAll(model);
            model_text1.getItems().sort(Comparator.naturalOrder());
            marka_box.getItems().addAll(marks);
            marka_box.getItems().sort(Comparator.naturalOrder());
            post_box.getItems().addAll(postav);
            post_box.getItems().sort(Comparator.naturalOrder());

            post_box.setOnAction(event -> {
                String selectedPost = post_box.getValue();
            });

            marka_box.setOnAction(event -> {
                String selectedMarka = marka_box.getValue();
            });
            model_text1.setOnAction(event -> {
                String selectedModel = model_text1.getValue();
            });

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для добавления продукта в базу данных
    public void addProduct() {
        if (age_text11.getText().isEmpty() || price_text.getText().isEmpty() || post_box.getValue().isEmpty() || descr_text.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, заполните все поля.");
            alert.showAndWait();
        } else {
            try {
                String dateProizvod = age_text11.getText(); // Получаем значение из текстового поля даты производства
                double price = Double.parseDouble(price_text.getText()); // Получаем значение из текстового поля цены
                String description = descr_text.getText(); // Получаем значение из текстового поля описания
                int manufacturId = getManufacturId(); // Получаем id производителя
                int markaId = getMarkaId(); // Получаем id марки
                String catalogautoPhoto = getCatalogautoPhoto(); // Получаем фото продукта
                int modelId = getModelId(); // Получаем id модели

                // Устанавливаем соединение с базой данных
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledb", "exampleuser", "examplepassword");

                // Создаем SQL-запрос для вставки данных в таблицу catalogauto
                String query = "INSERT INTO catalogauto (Date_proizvod, Price, descr, Manufactur_idManufactur,Marka_idMarka, catalogautoPhoto, model_idModel) VALUES (?, ?, ?, ?, ?, ?, ?)";

                // Создаем подготовленное выражение для выполнения запроса
                PreparedStatement statement = connection.prepareStatement(query);

                // Задаем значения параметров для запроса
                statement.setString(1, dateProizvod);
                statement.setDouble(2, price);
                statement.setString(3, description);
                statement.setInt(4, manufacturId);
                statement.setInt(5, markaId);
                statement.setString(6, catalogautoPhoto);
                statement.setInt(7, modelId);

                // Выполняем запрос
                int rowsAffected = statement.executeUpdate();

                // Проверяем, были ли изменены строки в таблице
                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Успех");
                    alert.setHeaderText(null);
                    alert.setContentText("Новый продукт успешно добавлен");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(null);
                    alert.setContentText("Новый продукт не добавлен");
                    alert.showAndWait();
                }

                // Закрываем соединение с базой данных
                connection.close();

                // Очищаем поля формы после добавления продукта
                age_text11.clear();
                price_text.clear();
                descr_text.clear();
                model_text11.clear();


            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Произошла ошибка при добавлении продукта: " + e.getMessage());
                alert.showAndWait();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Пожалуйста, введите корректное числовое значение: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    // Метод для получения id производителя
    public int getManufacturId() {
        int manufacturId = 0;

        try {
            String selectedManufactur = post_box.getValue(); // Получаем значение из текстового поля производителя
            System.out.println(selectedManufactur);
            // Устанавливаем соединение с базой данных
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledb", "exampleuser", "examplepassword");

            // Создаем SQL-запрос для получения id производителя
            String query = "SELECT idManufactur FROM manufactur WHERE Name_compani = ?";

            // Создаем подготовленное выражение для выполнения запроса
            PreparedStatement statement = connection.prepareStatement(query);

            // Задаем значение параметра для запроса
            statement.setString(1, selectedManufactur);
            System.out.println(statement);
            // Выполняем запрос и получаем результаты
            ResultSet resultSet = statement.executeQuery();

            // Если получен результат
            if (resultSet.next()) {
                System.out.println(resultSet);
                manufacturId = resultSet.getInt("idManufactur");
            }

            // Закрываем соединение с базой данных
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return manufacturId;
    }

    // Метод для получения id марки
    public int getMarkaId() {
        int markaId = 0;

        try {
            String selectedMarka = marka_box.getValue(); // Получаем выбранное значение из комбобокса марки

            System.out.println(selectedMarka);
            // Устанавливаем соединение с базой данных
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledb", "exampleuser", "examplepassword");

            // Создаем SQL-запрос для получения id марки
            String query = "SELECT idMarka FROM marka WHERE Marka_name = ?";

            // Создаем подготовленное выражение для выполнения запроса
            PreparedStatement statement = connection.prepareStatement(query);

            // Задаем значение параметра для запроса
            statement.setString(1, selectedMarka);
            // Выполняем запрос и получаем результаты
            ResultSet resultSet = statement.executeQuery();

            // Если получен результат
            if (resultSet.next()) {
                markaId = resultSet.getInt("idMarka");
            }

            // Закрываем соединение с базой данных
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(markaId);

        return markaId;
    }

    // Метод для получения фото продукта
    public String getCatalogautoPhoto() {
        String catalogautoPhoto = model_text11.getText();
        // Получаем фото продукта
        return catalogautoPhoto;
    }

    // Метод для получения id модели
    public int getModelId() {
        int modelId = 0;

        try {
            String selectedModel = model_text1.getValue(); // Получаем значение из текстового поля модели

            // Устанавливаем соединение с базой данных
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/exampledb", "exampleuser", "examplepassword");

            // Создаем SQL-запрос для получения id модели
            String query = "SELECT idModel FROM model WHERE Modelname = ?";

            // Создаем подготовленное выражение для выполнения запроса
            PreparedStatement statement = connection.prepareStatement(query);

            // Задаем значение параметра для запроса
            statement.setString(1, selectedModel);

            // Выполняем запрос и получаем результаты
            ResultSet resultSet = statement.executeQuery();

            // Если получен результат
            if (resultSet.next()) {
                modelId = resultSet.getInt("idModel");

            }

            // Закрываем соединение с базой данных
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(modelId);
        return modelId;
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
            model_text11.setText(file.getName());
        }
    }
    @FXML
    private void handleImageClicked(MouseEvent event) {
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

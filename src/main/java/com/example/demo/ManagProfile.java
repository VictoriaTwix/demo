package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ManagProfile {
    @FXML
    private ListView<AutoData> katalog_list;
    private ObservableList<AutoData> items;

    DB db = null;
    DBUsers dbUsers = null;
    @FXML
    private ImageView exit;
    private AutoData selectedProduct;


    @FXML
    void initialize() {
        // Инициализируем объект базы данных
        db = new DB();
        items = FXCollections.observableArrayList(); // Инициализируем ObservableList
        katalog_list.setItems(items); // Устанавливаем ObservableList в качестве элементов ListView
        loadInfo();

    }

    void loadInfo() {
        try {
            List<AutoData> ls = db.getProductNenal();
            items.addAll(ls);
            katalog_list.setCellFactory(stringListView -> {
                ListCell<AutoData> cell = new Autostr();
                ContextMenu contextMenu = new ContextMenu();

                // Создание кнопок
                MenuItem addButton = new MenuItem("Добавить");
                MenuItem editButton = new MenuItem("Редактировать");
                MenuItem deleteButton = new MenuItem("Удалить");

                // Добавление обработчиков событий
                addButton.setOnAction(event -> {
                    try {
                        addItem();
                        Stage loginStage = (Stage) exit.getScene().getWindow();
                        loginStage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                editButton.setOnAction(event -> {
                    try {
                        editItem(cell.getItem());
                        Stage loginStage = (Stage) exit.getScene().getWindow();
                        loginStage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                deleteButton.setOnAction(event -> {
                    try {
                        deleteItem(cell.getItem());
                        Stage loginStage = (Stage) exit.getScene().getWindow();
                        loginStage.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Добавление кнопок в контекстное меню
                contextMenu.getItems().addAll(addButton, editButton, deleteButton);

                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addItem() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add_catalog_product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 652, 426);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Добавление товаров в каталог");
    }

    private void editItem(AutoData item) throws IOException {
        // Реализация редактирования элемента
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("redactProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 671, 363);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Редактирование товаров в каталоге");
        // Получаем контроллер класса RedactProduct
        RedactProduct redactProductController = fxmlLoader.getController();
        // Вызываем метод setSelectedProduct и передаем в него объект AutoData
        redactProductController.setSelectedProduct(item);

    }

    private void deleteItem(AutoData item) throws SQLException {
        // Реализация удаления элемента
        String sql = "DELETE FROM catalogauto WHERE idCatalogAuto = ?";
        try {
            PreparedStatement preparedStmt = db.getConnection().prepareStatement(sql);
            preparedStmt.setInt(1, item.getIdAvto());
            int rowsAffected = preparedStmt.executeUpdate();
            if (rowsAffected > 0) {
                items.remove(item); // Удаляем элемент из ObservableList
                katalog_list.refresh(); // Принудительно обновляем ListView
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Удаление прошло успешно");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // Проверяем, является ли ошибка кодом '45000'
            if (e.getSQLState().equals("45000") ) {
                // Если код ошибки соответствует требованию, выводим сообщение об ошибке
                showErrorMessage("Произошла ошибка при удалении");
            } else {
                // Если код ошибки не соответствует требованию, пробрасываем исключение дальше
                showErrorMessage("Произошла ошибка при удалении");
                throw e;

            }
        }
    }
    @FXML
    private void handleImagVixod(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 954, 273);
            stage.setResizable(false);
            Stage loginStage = (Stage) exit.getScene().getWindow();
            loginStage.close();
            stage.setTitle("Главная страница");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}

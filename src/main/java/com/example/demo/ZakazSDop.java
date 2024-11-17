package com.example.demo;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


public class ZakazSDop {
    @FXML
    private ListView<DopData> list_color;
    BigDecimal fullPrice = null;

    @FXML
    private ListView<DopData> list_disk;

    @FXML
    private Label price_full;
    @FXML
    private ImageView exit;

    @FXML
    private Button zakaz;
    String x;

    @FXML
    private ListView<AutoData> list_avto;

    DB db = null;
    DBUsers dbUsers = null;
    @FXML
    private ListView<String> complect_avto;

    // Добавьте новые переменные для хранения id
    private String colorId;
    private String diskId;
    private String autoId;

    @FXML
    void initialize() {
        // Инициализируем объект базы данных
        db = new DB();
        loadInfo();
    }
    // Функция загрузки информации
    void loadInfo() {
        try {
            // Получаем данные о цветах из базы данных и добавляем в ListView list_color
            List<DopData> ls = db.getColor();
            list_color.getItems().addAll(ls);

            // Получаем данные о дисках из базы данных и добавляем в ListView list_disk
            List<DopData> lp = db.getDiski();
            list_disk.getItems().addAll(lp);
            // Получаем данные о автомобилях из базы данных и добавляем в ListView list_disk
            List<AutoData> la = db.getProduct();
            list_avto.getItems().addAll(la);

            // Настраиваем контекстное меню для списка цветов
            list_color.setCellFactory(stringListView -> {
                ListCell<DopData> cell = new Dopstr();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Добавить");

                // Действие при нажатии на кнопку "Добавить" в контекстном меню
                editItem.setOnAction(event -> {
                    DopData item = cell.getItem();
                    // Добавляем название выбранной строки в список complect_avto
                    complect_avto.getItems().add(item.getNameDop());
                    // Вызываем функцию для расчета полной стоимости автомобиля
                    calculateFullPrice();
                    // Сохраняйте id в переменные
                    colorId = String.valueOf(item.getIdDop());
                    list_color.setDisable(true);
                });

                contextMenu.getItems().addAll(editItem);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });

            // Настраиваем контекстное меню для списка дисков
            list_disk.setCellFactory(stringListView -> {
                ListCell<DopData> cell = new Dopstr();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Добавить");

                // Действие при нажатии на кнопку "Добавить" в контекстном меню
                editItem.setOnAction(event -> {
                    DopData item = cell.getItem();
                    // Добавляем название выбранной строки в список complect_avto
                    complect_avto.getItems().add(item.getNameDop());
                    // Вызываем функцию для расчета полной стоимости автомобиля
                    calculateFullPrice();
                    //  complect_avto.getItems().add(String.valueOf(item.getIdDop()));
                    // Сохраняйте id в переменные
                    diskId = String.valueOf(item.getIdDop());
                    list_disk.setDisable(true);
                });

                contextMenu.getItems().addAll(editItem);
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });
                return cell;
            });
            list_avto.setCellFactory(stringListView -> {
                ListCell<AutoData> cell = new Autostr();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Добавить");

                // Действие при нажатии на кнопку "Добавить" в контекстном меню
                editItem.setOnAction(event -> {
                    AutoData item = cell.getItem();
                    // Добавляем название выбранной строки в список complect_avto
                    complect_avto.getItems().add(item.getModelDop());
                    // Вызываем функцию для расчета полной стоимости автомобиля
                    calculateFullPrice();
                    // Сохраняйте id в переменные
                    autoId = String.valueOf(item.getIdAvto());
                    list_avto.setDisable(true);
                });

                contextMenu.getItems().addAll(editItem);
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

    void calculateFullPrice() {
        // Сбрасываем полную стоимость перед подсчетом
        fullPrice = BigDecimal.ZERO;
        // Получаем данные о цветах и дисках из ListView'ов list_color и list_disk
        ObservableList<DopData> selectedColors = list_color.getSelectionModel().getSelectedItems();
        ObservableList<DopData> selectedDisks = list_disk.getSelectionModel().getSelectedItems();
        ObservableList<AutoData> selectedAvto = list_avto.getSelectionModel().getSelectedItems();
        // Суммируем цены выбранных цветов
        for (DopData color : selectedColors) {
            fullPrice = fullPrice.add(color.getPriceDop());
        }

        // Суммируем цены выбранных дисков
        for (DopData disk : selectedDisks) {
            fullPrice = fullPrice.add(disk.getPriceDop());
        }
        for (AutoData avto : selectedAvto) {
            fullPrice = fullPrice.add(avto.getPrice());
        }
        // Выводим общую цену на метку price_full
        price_full.setText(fullPrice.toString());

    }



    public void handleZakaz(MouseEvent mouseEvent) throws IOException {
        DBUsers dbUsers = null;
        dbUsers = new DBUsers();
        Authorization authorization = new Authorization();
        // Получение данных о заказе
        int autoId = Integer.parseInt(this.autoId); // Идентификатор товара из каталога автомобилей
        int clientId = authorization.b; // Идентификатор клиента
        LocalDateTime orderDate = LocalDateTime.now(); // Текущая дата и время заказа
        int colorid = Integer.parseInt(colorId);
        int diskiid = Integer.parseInt(diskId);

        // Создание объекта для вставки данных в таблицу
        OrderData orderData = new OrderData(autoId, clientId, orderDate, colorid, diskiid);

        // Вставка данных заказа в базу данных
        boolean success = insertOrderData(orderData);

        // Проверка успешности операции
        if (success) {
            showMessage("Успешно", "Заказ успешно оформлен,в ближайшее время с вами свяжется менеджер");
        } else {
            showMessage("Ошибка", "Ошибка при оформлении заказа");
        }
    }

    // Метод для вставки данных заказа в базу данных
    private boolean insertOrderData(OrderData orderData) {
        try {

            // Установка соединения с базой данных
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:13306/exampledb", "exampleuser", "examplepassword");

            // Создание SQL-запроса для вставки данных заказа
            String query = "INSERT INTO catalogauto_has_clients VALUES (?, ?, ?, null, ?, ?,?)";

            // Создание PrepareStatement для выполнения SQL-запроса
            PreparedStatement statement = connection.prepareStatement(query);

            // Установка параметров SQL-запроса
            statement.setInt(1, orderData.getCatalogautoId());
            statement.setInt(2, orderData.getClientId());
            statement.setTimestamp(3, Timestamp.valueOf(orderData.getOrderDate()));
            statement.setString(4,"В работе");
            statement.setInt(5, orderData.getColor());
            statement.setInt(6, orderData.getDiski());


            // Выполнение SQL-запроса
            int rowsAffected = statement.executeUpdate();

            // Закрытие ресурсов
            statement.close();
            connection.close();

            // Проверка количества затронутых строк
            return rowsAffected > 0;
        } catch (SQLException e) {
            x = e.getMessage();
            showMessage("Ошибка", "" + x);
            e.printStackTrace();
            return false;
        }
    }

    // Класс для хранения данных заказа
    private class OrderData {
        private int color;
        private int catalogautoId;
        private int clientId;
        private LocalDateTime orderDate;

        private int diski;


        public OrderData(int catalogautoId, int clientId, LocalDateTime orderDate, int color, int diski) {
            this.catalogautoId = catalogautoId;
            this.clientId = clientId;
            this.orderDate = orderDate;
            this.color = color;
            this.diski = diski;

        }

        public int getCatalogautoId() {
            return catalogautoId;
        }

        public int getDiski() {
            return diski;
        }

        public int getColor() {
            return color;
        }

        public int getClientId() {
            return clientId;
        }

        public LocalDateTime getOrderDate() {
            return orderDate;
        }

    }

    //Вывод ошибок в всплывающем окне
    void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void Exit(MouseEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) exit.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Главная страница");
                stage.setScene(scene);
                stage.show();
    }
}


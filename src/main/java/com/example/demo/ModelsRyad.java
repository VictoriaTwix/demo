package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModelsRyad {
    // Объявляем элементы интерфейса
    @FXML
    private Label audi_l;

    @FXML
    private Label bmw_l;

    @FXML
    private ListView<AutoData> modelryad;

    @FXML
    private ComboBox<String> marka_box;

    @FXML
    private ImageView vixod_img;

    @FXML
    private ComboBox<String> price_box;


    @FXML
    private TextField poisk;

    // Объявляем объекты базы данных
    DBUsers dbUsers = null;
    DB db = null;
//Метод сортировки по цене
    @FXML
    private void handleSortByPrice() {
        String selectedSortOption = price_box.getValue();
        if ("По возрастанию".equals(selectedSortOption)) {
            modelryad.getItems().sort(Comparator.comparing(AutoData::getPrice));
        } else if ("По убыванию".equals(selectedSortOption)) {
            modelryad.getItems().sort(Comparator.comparing(AutoData::getPrice).reversed());
        }
    }

    ObservableList<AutoData> studentDataList;

    // Инициализация
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        // Инициализируем объект базы данных
        db = new DB();
        loadInfo();
        ObservableList<String> sortOptions = FXCollections.observableArrayList("По возрастанию", "По убыванию");
        price_box.setItems(sortOptions);
        price_box.setValue("По возрастанию"); // Устанавливаем значение по умолчанию

        price_box.setOnAction(event -> handleSortByPrice());

        List<AutoData> ls = db.getProductNenal();
        studentDataList = FXCollections.observableArrayList(ls);
        modelryad.setItems(studentDataList);


        // Загружаем список марок и моделей
        List<String> marks = db.getmarka();
        List<String> Models = db.getmodel();
        marka_box.getItems().addAll(marks);
        marka_box.getItems().sort(Comparator.naturalOrder());

        // Обработчики событий для комбобоксов
        marka_box.setOnAction(event -> {
            String selectedMarka = marka_box.getValue();
            List<AutoData> searchResults = studentDataList.stream()
                    .filter(product -> product.getMarka().equals(selectedMarka))
                    .collect(Collectors.toList());
            if (searchResults.isEmpty()) {
                // Если значения не найдены, выводим сообщение об ошибке и сбрасываем значения
                showAlert("Ошибка", "Выбранная марка не найдена в базе данных");
                resetComboBoxesAndListView();
            } else {
                updateListView(searchResults);
            }
        });


    }

    // Метод для сброса значений комбобоксов и списка
    private void resetComboBoxesAndListView() {
        modelryad.setItems(studentDataList);
        marka_box.setValue(null);

    }

    // Метод для обновления списка
    private void updateListView(List<AutoData> searchResults) {
        modelryad.setItems(FXCollections.observableArrayList(searchResults));
    }

    // Метод для загрузки информации из базы данных в список
    void loadInfo() {
        try {
            List<AutoData> ls = db.getProductNenal();
            modelryad.getItems().addAll(ls);
            modelryad.setCellFactory(stringListView -> {
                ListCell<AutoData> cell = new Autostr();
                ContextMenu contextMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("");
                editItem.setOnAction(event -> {
                    AutoData item = cell.getItem();
                    System.out.println(item.getModelDop());
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

    // Обработчик события при нажатии на изображение
    @FXML
    private void handleImageClicked(MouseEvent event) {
        try {
            dbUsers = new DBUsers();
            Authorization authorization = new Authorization();
            int clientId = authorization.cl;
            System.out.println(clientId);
            // Загружаем fxml файл для новой сцены
            if (clientId == 2) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ManagerCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_img.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Менеджера");
                stage.setScene(scene);
                stage.show();

            }
            else if (clientId == 1) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DirectorCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_img.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Директора");
                stage.setScene(scene);
                stage.show();
            }
            else if (clientId == 0) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_img.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Главная страница");
                stage.setScene(scene);
                stage.show();

            }
            else if (clientId == 3) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_img.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Пользователя");
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Unknown client ID: " + clientId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для отображения всплывающего сообщения
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

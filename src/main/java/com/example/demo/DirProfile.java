package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirProfile {
    @FXML
    private TableView<SalesReport> salesReportTable;
    @FXML
    private ImageView vixod_otch;

    @FXML
    private TableColumn<SalesReport, String> c1Column;
    @FXML
    private TableColumn<SalesReport, String> c2Column;

    DBUsers dbUsers = null;
    public void initialize() {
        // Связывание столбцов с данными
        c1Column.setCellValueFactory(new PropertyValueFactory<>("monthYear"));
        c2Column.setCellValueFactory(new PropertyValueFactory<>("totalSales"));
        updateSalesReportTable();
    }

    public void updateSalesReportTable() {
        List<SalesReport> report = generateSalesReport();

        ObservableList<SalesReport> data = FXCollections.observableArrayList(report);
        salesReportTable.setItems(data);
    }

    private List<SalesReport> generateSalesReport() {
        List<SalesReport> report = new ArrayList<>();
        try {
            // Выполнение процедуры generate_sales_report
            Connection conn = DB.getConnection();
            CallableStatement stmt = conn.prepareCall("{call generate_sales_report()}");
            System.out.println(stmt);
            stmt.execute();

            // Получение результатов
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                String monthYear = rs.getString("month_year");
                double totalSales = rs.getDouble("total_sales");
                System.out.println(String.format("%.2f", totalSales));
                report.add(new SalesReport(monthYear, String.format("%.2f", totalSales)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    @FXML
    private void handleImagVixod(MouseEvent event) {
        try {
            dbUsers = new DBUsers();
            Authorization authorization = new Authorization();
            int clientId = authorization.b;
            System.out.println(clientId);
            // Загружаем fxml файл для новой сцены
            if (clientId == 2) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ManagerCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_otch.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Менеджера");
                stage.setScene(scene);
                stage.show();

            } else if (clientId == 1) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DirectorCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_otch.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Личный кабинет Директора");
                stage.setScene(scene);
                stage.show();
            } else if (clientId == 0) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_otch.getScene().getWindow();
                loginStage.close();
                stage.setTitle("Главная страница");
                stage.setScene(scene);
                stage.show();

            } else if (clientId == 3) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientCabin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 954, 273);
                stage.setResizable(false);
                Stage loginStage = (Stage) vixod_otch.getScene().getWindow();
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

    public void handleImag(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GlavStr.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 954, 273);
        stage.setResizable(false);
        Stage loginStage = (Stage) vixod_otch.getScene().getWindow();
        loginStage.close();
        stage.setTitle("Главная страница");
        stage.setScene(scene);
        stage.show();
    }
}

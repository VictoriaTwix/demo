package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    private static Connection connection;
    private final String HOST = "localhost";
    private final String PORT = "13306";
    private final String DB_NAME = "exampledb";
    private final String LOGIN = "exampleuser";
    private final String PASS = "examplepassword";

    private Connection dbConn = null;
    private static final String CATALOG_AUTO_TABLE = "catalogauto";
    private static final String DOP_COLOR_TABLE = "dop_color";
    private static final String DOP_DISKI_TABLE = "dop_diski";
    private static Connection conn = null;

    public static Connection getConnection() {

        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:13306/exampledb";
                String username = "exampleuser";
                String password = "examplepassword";
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }


    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?characterEncoding=UTF8";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }
    //Получение списка товаров из базы данных которые есть в наличии
    public List<AutoData> getProduct() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ma.Marka_name,md.Modelname,c.catalogautoPhoto, c.Marka_idMarka, c.model_idModel, c.Price, c.Date_proizvod,c.descr,c.idCatalogAuto FROM " + CATALOG_AUTO_TABLE + " c JOIN marka ma ON c.Marka_idMarka = ma.idMarka JOIN model md ON c.model_idModel = md.idModel WHERE c.Status_nal = 'в наличии'  ORDER BY c.idCatalogAuto DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        List<AutoData> products = new ArrayList<>();
        while (res.next()) {
            products.add(new AutoData(
                    res.getInt("idCatalogAuto"),
                    res.getString("descr"),
                    res.getString("catalogautoPhoto"),
                    res.getString("Marka_name"),
                    res.getString("Modelname"),
                    res.getBigDecimal("Price"),
                    res.getString("Date_proizvod"))
            );
        }
        return products;
    }
    //Получение всего списка товаров из базы данных
    public ArrayList<AutoData> getProductNenal() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ma.Marka_name,md.Modelname,c.catalogautoPhoto, c.Marka_idMarka, c.model_idModel, c.Price, c.Date_proizvod,c.descr,c.idCatalogAuto FROM catalogauto c JOIN marka ma ON c.Marka_idMarka = ma.idMarka JOIN model md ON c.model_idModel = md.idModel WHERE c.Status_nal = 'в наличии' or c.Status_nal = 'нет в наличии' ORDER BY c.idCatalogAuto DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<AutoData> prod = new ArrayList<>();
        while (res.next())
            prod.add(new AutoData(res.getInt("idCatalogAuto"), res.getString("descr"), res.getString("catalogautoPhoto"), res.getString("Marka_name"), res.getString("Modelname"), res.getBigDecimal("Price"), res.getString("Date_proizvod")));
        return prod;
    }
    //Получение списка цветов из базы данных
    public List<DopData> getColor() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM " + DOP_COLOR_TABLE + " ORDER BY idDop_color DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        List<DopData> colors = new ArrayList<>();
        while (res.next()) {
            colors.add(new DopData(
                            res.getInt("idDop_color"), res.getString("Dop_color_name"),
                            res.getBigDecimal("Dop_color_Price"),
                            res.getString("Dop_colorPhoto")
                    )
            );
        }
        return colors;
    }
    //Получение списка дисков из базы данных

    public List<DopData> getDiski() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM " + DOP_DISKI_TABLE + " ORDER BY idDop_diski DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        List<DopData> disks = new ArrayList<>();
        while (res.next()) {
            disks.add(new DopData(
                            res.getInt("idDop_diski"), res.getString("Dop_diskiName"),
                            res.getBigDecimal("Dop_diskiPrice"),
                            res.getString("Dop_diski_photo")
                    )
            );
        }
        return disks;
    }
    //Получение списка моделей из базы данных
    public ArrayList<String> getmodel() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Modelname FROM model";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> model = new ArrayList<>();
        while (res.next()) {
            model.add(res.getString("Modelname"));
        }
        return model;
    }
    //Получение списка марок из базы данных
    public ArrayList<String> getmarka() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Marka_name FROM marka";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> marka = new ArrayList<>();
        while (res.next()) {
            marka.add(res.getString("Marka_name"));
        }
        return marka;
    }

    public ArrayList<String> getpostav() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Name_compani FROM manufactur";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> postav = new ArrayList<>();
        while (res.next()) {
            postav.add(res.getString("Name_compani"));
        }
        return postav;
    }
    //Обновление данных после редактирования данных в личном кабинете менеджера
    public void updateProduct(int productId, String newDateProizvod, String newPrice, String newStatusNal, String newDescr, String newCatalogautoPhoto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Устанавливаем соединение с базой данных
            connection = getConnection();

            // Создаем SQL-запрос для обновления значений продукта
            String query = "UPDATE catalogauto SET Date_proizvod = ?, Price = ?, Status_nal = ?, descr = ?, catalogautoPhoto = ? WHERE idCatalogAuto = ?";
            statement = connection.prepareStatement(query);

            // Устанавливаем значения параметров в SQL-запросе
            statement.setString(1, newDateProizvod);
            statement.setString(2, newPrice);
            statement.setString(3, newStatusNal);
            statement.setString(4, newDescr);
            statement.setString(5, newCatalogautoPhoto);
            statement.setInt(6, productId);

            // Выполняем SQL-запрос
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Закрываем соединение и освобождаем ресурсы
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}




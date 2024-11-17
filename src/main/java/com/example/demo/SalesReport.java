package com.example.demo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesReport {
    private final SimpleStringProperty monthYear = new SimpleStringProperty("");
    private final SimpleStringProperty totalSales = new SimpleStringProperty("");

    public SalesReport(String monthYear, String totalSales) {
        this.monthYear.set(monthYear);
        this.totalSales.set(totalSales);
    }

    public String getMonthYear() {
        return monthYear.get();
    }

    public String getTotalSales() {
        return totalSales.get();
    }

}

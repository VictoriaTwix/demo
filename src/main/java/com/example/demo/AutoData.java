package com.example.demo;

import java.math.BigDecimal;

public class AutoData {

    private String photo;
    private String deskr;
    private String marka;
    private String model;
    private BigDecimal price;
    private String date_a;
    private int id;
    private int markaId;
    private String nal;

    public AutoData(int id, String deskr, String photo, String marka, String model, BigDecimal price, String date_a) {
        this.id = id;
        this.deskr = deskr;
        this.photo = photo;
        this.marka = marka;
        this.model = model;
        this.price = price;
        this.date_a = date_a;
    }

    public String getNal() {
        return this.nal;

    }

    public String getPhoto() {
        return this.photo;

    }

    public int getIdAvto() {

        return this.id;
    }


    public String getMarka() {
        return marka;
    }

    public String getModelDop() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }


    public String getDeskr() {

        return deskr;
    }

    public String getDate_a() {
        return date_a;
    }
}


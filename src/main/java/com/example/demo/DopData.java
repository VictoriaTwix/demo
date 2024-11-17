package com.example.demo;

import java.math.BigDecimal;

public class DopData {
    private String Name;
    private String photodop;
    private BigDecimal pricedop;
    private int iddop;

    public DopData(int iddop, String Name, BigDecimal pricedop, String photodop) {
        this.Name = Name;
        this.photodop = photodop;
        this.pricedop = pricedop;
        this.iddop = iddop;
    }
    public String getPhotodop() {
        return this.photodop;
    }

    public String getNameDop() {
        return this.Name;
    }

    public BigDecimal getPriceDop() {
        return pricedop;
    }

    public int getIdDop() {
        return iddop;
    }
}




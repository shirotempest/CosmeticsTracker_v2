package com.gmail.sirotempest.data.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import com.gmail.sirotempest.data.db.converter.DateConverter;

@Entity(tableName = "products")
@TypeConverters(DateConverter.class)
public class Product {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String brand;
    public Date expiryDate;
    public int quantity;
    public int price;
    public int status;
    
    @Ignore
    public Product() {
        this.name = "";
        this.brand = "";
        this.expiryDate = null;
        this.quantity = 0;
        this.price = 0;
        this.status = 0;
    }

    public Product(String name, String brand, Date expiryDate, int quantity, int price, int status) {
        this.name = name;
        this.brand = brand;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }
}

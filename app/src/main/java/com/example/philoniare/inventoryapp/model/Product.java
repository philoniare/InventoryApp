package com.example.philoniare.inventoryapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    @PrimaryKey
    private String name;
    private int quantity;
    private Double price;
}

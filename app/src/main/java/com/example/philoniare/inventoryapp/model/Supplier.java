package com.example.philoniare.inventoryapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Supplier extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;

    public Supplier() {}

    public Supplier(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

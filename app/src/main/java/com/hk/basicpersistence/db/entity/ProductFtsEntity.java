package com.hk.basicpersistence.db.entity;


import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "productsFts")
@Fts4(contentEntity = ProductEntity.class)
public class ProductFtsEntity {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

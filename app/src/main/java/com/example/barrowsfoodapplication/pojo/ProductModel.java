package com.example.barrowsfoodapplication.pojo;

public class ProductModel {
    public int id;
    public String name;
    public String productImageUrl;
    public String description;

    public ProductModel(){}

    public ProductModel(String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getDescription() {
        return description;
    }
}

package com.rikkeifood.model;

public class Food {
    private String id;
    private String name;
    private String category;
    private double price;
    private String physicalImagePath;

    public Food() {
    }

    public Food(String id, String name, String category, double price, String physicalImagePath) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.physicalImagePath = physicalImagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhysicalImagePath() {
        return physicalImagePath;
    }

    public void setPhysicalImagePath(String physicalImagePath) {
        this.physicalImagePath = physicalImagePath;
    }
}
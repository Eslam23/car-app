package com.example.carapp;

public class Cars {
    private int id;
    private String model;
    private String color;
    private String image;
    private String description;
    private double dbl;

    public Cars(String model, String color, String image, String description, double dbl) {
        this.model = model;
        this.color = color;
        this.image = image;
        this.description = description;
        this.dbl = dbl;
    }

    public Cars(int id, String model, String color, String image, String description, double dbl) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.image = image;
        this.description = description;
        this.dbl = dbl;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public double getDbl() {
        return dbl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDbl(double dbl) {
        this.dbl = dbl;
    }
}

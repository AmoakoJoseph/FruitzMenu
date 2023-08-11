package com.example.fruitsapp;

public class Fruit {
    private String name;
    private String imgSrc;
    private String color;
    private String description;

    private String additional_info;

    public Fruit() {

    }

    public Fruit(String name, String imgSrc, String color) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getdescription() { return description; }

    public void setdescription(String description) {this.description = description;}

    public String getadditional_info() {return additional_info;}

    public void setadditional_info(String additional_info) {this.additional_info = additional_info;}
}

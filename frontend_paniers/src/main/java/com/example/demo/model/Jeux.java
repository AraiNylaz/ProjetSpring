package com.example.demo.model;

import lombok.Data;

@Data
public class Jeux {

    private int id;
    private String name;
    private String short_description;
    private String detailed_description;
    private double price;
    private String category;

    public Jeux(){
    }

    public Jeux(int id, String name, String short_description, String detailed_description, double price, String category) {
        this.id = id;
        this.name = name;
        this.short_description = short_description;
        this.detailed_description = detailed_description;
        this.price = price;
        this.category = category;
    }

}

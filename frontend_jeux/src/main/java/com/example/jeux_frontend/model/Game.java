package com.example.jeux_frontend.model;


import lombok.Data;

@Data
public class Game {

    private int id;
    private String name;
    private String short_description;
    private String detailed_description;
    private double price;
    private String category;
}

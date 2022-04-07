package com.example.jeux_frontend.model;

import lombok.Data;

@Data
public class Panier {

    private int id;
    private int idUser;
    private int idGame;
    private int quantity;
}

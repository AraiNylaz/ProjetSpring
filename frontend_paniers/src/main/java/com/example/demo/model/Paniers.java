package com.example.demo.model;

import lombok.Data;

@Data
public class Paniers {

    private int id;
    private int idUser;
    private int idProduit;
    private int quantite;

    public Paniers() { }

    public Paniers(int id, int idUser, int idProduit, int quantite){
        this.id = id;
        this.idUser = idUser;
        this.idProduit = idProduit;
        this.quantite = quantite;
    }

}

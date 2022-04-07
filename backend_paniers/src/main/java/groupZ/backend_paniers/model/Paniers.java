package groupZ.backend_paniers.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "paniers")
public class Paniers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
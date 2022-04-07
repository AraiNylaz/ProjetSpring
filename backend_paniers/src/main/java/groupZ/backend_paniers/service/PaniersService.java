package groupZ.backend_paniers.service;

import groupZ.backend_paniers.model.Paniers;
import groupZ.backend_paniers.PaniersRepository.PaniersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaniersService {

    private final PaniersRepository repo;

    public PaniersService(PaniersRepository repo){
        this.repo = repo;
    }



    public Iterable<Paniers> findByUserId(int id){
        return repo.findByIdUser(id);
    }

    public Iterable<Paniers> findAll() {
        return repo.findAll();
    }

    public Paniers findPanier( int id) {
        return repo.findById(id);
    }

    public Paniers savePanier(Paniers panier) {
        return repo.save(panier);
    }

    public Paniers updatePanier(Paniers paniers) {
        System.out.println(paniers.getId());
        Paniers panier=repo.findById(paniers.getId());
        panier.setQuantite(paniers.getQuantite());
        return repo.save(panier);
    }

    public void deletePanier(int id) {
        repo.deleteById(id);
    }
}

package com.example.jeux.service;

import com.example.jeux.model.Jeux;
import com.example.jeux.repository.JeuxRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JeuxService {

    private final JeuxRepository repository;

    public JeuxService(JeuxRepository jeuxRepository){
        this.repository=jeuxRepository;
    }

    public Iterable<Jeux> findAllJeux(String direction){
        if(direction==null || (!direction.equals("asc") && !direction.equals("desc")))
            return repository.findAll();
        else
            if (direction.equals("desc"))
                return repository.findAllByOrderByPriceDesc();
            else
                return repository.findAllByOrderByPriceAsc();
    }

    public Iterable<Jeux> findByCategorie(String category,String direction){
        if(direction==null || (!direction.equals("asc") && !direction.equals("desc")))
            return repository.findAllByCategory(category);
        else
            if(direction.equals("desc"))
                return repository.findAllByCategoryOrderByPriceDesc(category);
            else
                return repository.findAllByCategoryOrderByPriceAsc(category);
    }

    public Jeux getJeux(int id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Object with the ID "+id));
    }

    public Jeux addJeux(Jeux jeux){
        return repository.save(jeux);
    }
    public void deleteJeuxById(int id){
        repository.deleteById(id);
    }

    public Jeux updateJeux(int id,Jeux jeux){
        Jeux jeuxToUpdate=repository.findById(id).orElseThrow(InternalError::new);
        jeuxToUpdate.setName(jeux.getName());
        jeuxToUpdate.setCategory(jeux.getCategory());
        jeuxToUpdate.setPrice(jeux.getPrice());
        jeuxToUpdate.setShort_description(jeux.getShort_description());
        jeuxToUpdate.setDetailed_description(jeux.getDetailed_description());
        return repository.save(jeuxToUpdate);
    }

    public Iterable<Jeux> jeuxFilteredByPrice(double priceMin,double priceMax,String direction){
        if(priceMin>priceMax && priceMax!=-1){
            double a=priceMin;
            priceMin=priceMax;
            priceMax=a;
        }
        if(priceMax==-1) priceMax=repository.getPrixMax();

        if(direction==null || (!direction.equals("asc") && !direction.equals("desc")))
            return repository.findAllFilteredByPrice(priceMin,priceMax);
        else
            if(direction.equals("desc"))
                return repository.findAllFilteredByPriceOrderByPriceDesc(priceMin,priceMax);
            else
                return repository.findAllFilteredByPriceOrderByPriceAsc(priceMin,priceMax);
    }

    public Iterable<Jeux> jeuxFilteredByPriceAndCategory(String category, double priceMin,double priceMax,String direction){
        if(priceMin>priceMax && priceMax!=-1){
            double a=priceMin;
            priceMin=priceMax;
            priceMax=a;
        }
        if(priceMax==-1) priceMax=repository.getPrixMax();

        if(direction==null || (!direction.equals("asc") && !direction.equals("desc")))
            return repository.findAllFilteredByPriceAndCategory(category,priceMin,priceMax);
        else
        if(direction.equals("desc"))
            return repository.findAllFilteredByPriceAndCategoryOrderByPriceDesc(category,priceMin,priceMax);
        else
            return repository.findAllFilteredByPriceAndCategoryOrderByPriceAsc(category,priceMin,priceMax);

    }
}

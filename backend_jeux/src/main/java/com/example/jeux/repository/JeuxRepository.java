package com.example.jeux.repository;

import com.example.jeux.model.Jeux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JeuxRepository extends CrudRepository<Jeux,Integer> {

    public Iterable<Jeux> findAllByOrderByPriceAsc();

    public Iterable<Jeux> findAllByOrderByPriceDesc();

    public Iterable<Jeux> findAllByCategory(String category);

    public Iterable<Jeux> findAllByCategoryOrderByPriceDesc(String category);

    public Iterable<Jeux> findAllByCategoryOrderByPriceAsc(String category);

    @Query("SELECT j FROM jeux j WHERE j.price>=?1 AND j.price<=?2")
    public Iterable<Jeux> findAllFilteredByPrice(double priceMin,double priceMax);

    @Query("SELECT j FROM jeux j WHERE j.price>=?1 AND j.price<=?2 order by j.price desc")
    public Iterable<Jeux> findAllFilteredByPriceOrderByPriceDesc(double priceMin,double priceMax);

    @Query("SELECT j FROM jeux j WHERE j.price>=?1 AND j.price<=?2 order by j.price asc")
    public Iterable<Jeux> findAllFilteredByPriceOrderByPriceAsc(double priceMin,double priceMax);

    @Query("SELECT j FROM jeux j WHERE j.category=?1 AND j.price>=?2 AND j.price<=?3")
    public Iterable<Jeux> findAllFilteredByPriceAndCategory(String category,double priceMin,double priceMax);

    @Query("SELECT j FROM jeux j WHERE j.category=?1 AND j.price>=?2 AND j.price<=?3 order by j.price desc")
    public Iterable<Jeux> findAllFilteredByPriceAndCategoryOrderByPriceDesc(String category,double priceMin,double priceMax);

    @Query("SELECT j FROM jeux j WHERE j.category=?1 AND j.price>=?2 AND j.price<=?3 order by j.price asc ")
    public Iterable<Jeux> findAllFilteredByPriceAndCategoryOrderByPriceAsc(String category,double priceMin,double priceMax);

    @Query("SELECT MAX(j.price) FROM jeux j")
    public double getPrixMax();
}

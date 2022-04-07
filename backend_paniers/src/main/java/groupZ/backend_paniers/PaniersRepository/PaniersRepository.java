package groupZ.backend_paniers.PaniersRepository;

import groupZ.backend_paniers.model.Paniers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaniersRepository extends CrudRepository<Paniers, Integer>{

    Iterable<Paniers> findByIdUser(int id);

    Paniers findByIdUserAndIdProduit(int userId, int productId);

    Paniers findByIdAndAndIdUser(int id,int userId);

    Paniers findById(int id);

}

package bg.softuni.eliteSportsEquipment.repository;

import bg.softuni.eliteSportsEquipment.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AllProductsRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.type = :type")
    Long findCountProductsByType(@Param(value = "type") String type);

    //    @Query("SELECT c FROM ProductEntity c WHERE c.type = :type")
    //    List<ProductEntity> findAllProductsByType(String type);
    //
    //    Optional<ProductEntity> findByBrand(String brand);
}

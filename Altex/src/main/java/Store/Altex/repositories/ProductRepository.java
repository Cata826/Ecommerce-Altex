package Store.Altex.repositories;

import Store.Altex.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Modifying
    @Query("UPDATE Product p SET p.stars = :stars WHERE p.id = :productId")
    int updateStars(@Param("productId") Long productId, @Param("stars") int stars);
}

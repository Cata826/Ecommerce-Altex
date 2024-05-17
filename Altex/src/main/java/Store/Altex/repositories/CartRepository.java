package Store.Altex.repositories;

import Store.Altex.models.Cart;
import Store.Altex.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT w FROM Cart w JOIN FETCH w.user u JOIN FETCH w.product p")
    List<Cart> findAllWithUserAndProduct();

    @Query("SELECT w.product FROM Cart w WHERE w.user.id = :userId")
    List<Product> findProductsByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Cart w WHERE w.user.id = :userId")
    List<Cart> findByUserId(@Param("userId") Long userId);

}
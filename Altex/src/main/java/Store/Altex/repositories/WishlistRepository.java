package Store.Altex.repositories;

import Store.Altex.models.Product;
import Store.Altex.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    @Query("SELECT w FROM Wishlist w JOIN FETCH w.user u JOIN FETCH w.product p")
    List<Wishlist> findAllWithUserAndProduct();

    @Query("SELECT w.product FROM Wishlist w WHERE w.user.id = :userId")
    List<Product> findProductsByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId")
    List<Wishlist> findByUserId(@Param("userId") Long userId);
}

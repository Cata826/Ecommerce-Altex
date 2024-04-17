package Store.Altex.repositories;

import Store.Altex.models.Cart;
import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT w FROM Order w JOIN FETCH w.user u JOIN FETCH w.product p")
    List<Order> findAllWithUserAndProduct();

    @Query("SELECT w.product FROM Order w WHERE w.user.id = :userId")
    List<Product> findProductsByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Order w WHERE w.user.id = :userId")
    List<Order> findByUserId(@Param("userId") Long userId);
    @Query("SELECT o.user FROM Order o WHERE o.user.id = :userId")
    Optional<User> findUserByUserId(@Param("userId") Long userId);
}

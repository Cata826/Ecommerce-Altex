package Store.Altex.repositories;

import Store.Altex.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review> findByUserIdAndProductId(Long productId, Long userId);
    List<Review> findByProductId(Long productId);


}

package Store.Altex.repositories;


import Store.Altex.models.User;
import Store.Altex.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByLastLogged(LocalDateTime lastLogged);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);



    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.lastLogged = :lastLogged")
    void updateLastLoggedForAllUsers(LocalDateTime lastLogged);

}
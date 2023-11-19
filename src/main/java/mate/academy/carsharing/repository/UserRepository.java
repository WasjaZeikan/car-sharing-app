package mate.academy.carsharing.repository;

import java.util.Optional;
import mate.academy.carsharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}

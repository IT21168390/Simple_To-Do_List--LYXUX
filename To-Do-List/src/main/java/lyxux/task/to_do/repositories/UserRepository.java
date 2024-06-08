package lyxux.task.to_do.repositories;

import lyxux.task.to_do.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
    User findFirstByEmail(String email);
    boolean existsByEmail(String email);
}

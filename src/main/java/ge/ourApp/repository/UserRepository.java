package ge.ourApp.repository;

import ge.ourApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String login);

    Optional<User> findByConfirmUUID(UUID uuid);
}

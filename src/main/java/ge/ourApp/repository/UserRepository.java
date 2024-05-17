package ge.ourApp.repository;

import ge.ourApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByFirstName(String firstname);
    Optional<User> findByLogin(String login);
}
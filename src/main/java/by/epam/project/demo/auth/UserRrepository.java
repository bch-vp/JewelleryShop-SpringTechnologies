package by.epam.project.demo.auth;

import by.epam.project.model.entity.User;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRrepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    @Query(value = "SELECT password FROM users  WHERE login = ?", nativeQuery = true)
    Optional<String> findPasswordByLogin(String login);
}

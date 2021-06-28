package by.epam.project.repository;

import by.epam.project.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByTelephoneNumber(String telephoneNumber);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT avatar_url FROM users  WHERE BINARY login = ?", nativeQuery = true)
    Optional<String> findAvatarURLByLogin(String login);

    @Query(value = "SELECT password FROM users  WHERE BINARY login = ?", nativeQuery = true)
    Optional<String> findPasswordByLogin(String login);

    @Query(value = "SELECT users.id AS id, login, password, first_name, last_name, telephone_number, email, role, status " +
            "FROM users " +
            "WHERE role != 'ADMIN'", nativeQuery = true)
    List<User> findAllClients();


    @Query(value = "UPDATE users SET avatar_url = ? WHERE BINARY login = ?", nativeQuery = true)
    Boolean updateAvatarURLByLogin(String avatarUrl, String login);

    @Query(value = "UPDATE users SET password = ? WHERE BINARY login = ?", nativeQuery = true)
    Boolean updatePasswordByLogin(String password, String login);

    @Query(value = "UPDATE users SET status = ? WHERE id = ?", nativeQuery = true)
    Boolean updateStatusById(String status, long id);

    @Query(value = "UPDATE users SET status = ? WHERE BINARY login = ?", nativeQuery = true)
    Boolean updateStatusByLogin(String status, String login);

    @Query(value = "UPDATE users SET avatar_url = NULL WHERE BINARY login = ?", nativeQuery = true)
    Boolean removeAvatarUrlByLogin(String login);
}
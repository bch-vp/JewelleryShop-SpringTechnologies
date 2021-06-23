package by.epam.project.repository;

import by.epam.project.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<User, Long> {
}

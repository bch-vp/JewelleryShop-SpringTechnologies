package by.epam.project.repository;

import by.epam.project.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<User, Long> {
}

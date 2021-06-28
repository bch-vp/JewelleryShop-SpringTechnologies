package by.epam.project.repository;

import by.epam.project.entity.Category;
import by.epam.project.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findAll();


    @Query(value = "UPDATE categories SET name = ? WHERE id = ?", nativeQuery = true)
    Boolean updateNameById(String name, Long id);
}

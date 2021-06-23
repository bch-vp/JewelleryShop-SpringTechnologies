package by.epam.project.repository;

import by.epam.project.entity.Product;
import by.epam.project.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}

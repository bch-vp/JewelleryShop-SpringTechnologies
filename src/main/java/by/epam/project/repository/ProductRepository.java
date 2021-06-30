package by.epam.project.repository;

import by.epam.project.entity.Category;
import by.epam.project.entity.Product;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("select p from Product p " +
            "join fetch p.category " +
            "where p.category.name = ?1 and p.status <> 'BLOCKED'")
    List<Product> findAllProductsByCategoryToClient(String categoryName);

    @Query("select p from Product p " +
            "join fetch p.category " +
            "where p.category.name = ?1")
    List<Product> findAllProductsByCategoryToAdmin(String categoryName);

    @Query("select p.imageURL from Product p " +
            "where p.category.name = ?1")
    Optional<String> findImageURLByProductName(String name);

    @Query("update Product p " +
            "set p.category = :idCategory " +
            "where p.id = :idProduct ")
    Boolean updateProductCategory(Long idCategory, Long idProduct);

    @Query("update Product p " +
            "set p.imageURL = :imageUrl " +
            "where p.name = :name ")
    Boolean updateImageURLByProductName(String name, String imageUrl);

    @Query("update Product p " +
            "set p.status = :idStatus " +
            "where p.id = :idProduct ")
    Boolean updateProductStatus(Long idProduct, Integer idStatus);

    @Query("update Product p " +
            "set p.name = :name, p.info = :info, p.price = :price " +
            "where p.id = :id ")
    Boolean updateProductInfo(Long id, String name, String info, BigDecimal price);
}

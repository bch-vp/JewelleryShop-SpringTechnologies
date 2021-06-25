package by.epam.project.repository;

import by.epam.project.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query("select o from Order o " +
            "join fetch o.products " +
            "where o.id = :id")
    Optional<List<Order>> findAllOrdersByUserId(long id);

    @Query("select o from Order o join fetch o.products")
    Optional<List<Order>> findAllOrders(long id);


    @Query(value = "UPDATE orders SET status = ? WHERE BINARY id = ?", nativeQuery = true)
    Boolean updateOrderStatusById(String status, long id);
}

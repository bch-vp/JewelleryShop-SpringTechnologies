package by.epam.project.builder;

import by.epam.project.entity.Category;
import by.epam.project.entity.Order;
import by.epam.project.entity.Product;
import by.epam.project.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderBuilder {
    private Long id;
    private String comment;
    private String address;
    private Date timeCreated;
    private BigDecimal totalPrice;
    private Order.Status status;
    private User user;
    private Set<Product> products = new HashSet<>();

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public OrderBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public OrderBuilder setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public OrderBuilder setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderBuilder setStatus(Order.Status status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public OrderBuilder setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }


    public Order build() {
        return new Order(id, comment,  address, timeCreated, totalPrice, status, user, products);
    }
}

package by.epam.project.builder;

import by.epam.project.entity.Category;
import by.epam.project.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class ProductBuilder {
    private Long id;
    private String name;
    private String info;
    private Product.Status status;
    private BigDecimal price;
    private String imageURL;
    private Category category;

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setInfo(String info) {
        this.info = info;
        return this;
    }

    public ProductBuilder setStatus(Product.Status status) {
        this.status = status;
        return this;
    }

    public ProductBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public ProductBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }


    public Product build() {
        return new Product(id, name, info, status, price, imageURL, category);
    }
}

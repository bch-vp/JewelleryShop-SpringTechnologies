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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatus(Product.Status status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }


    public Product build() {
        return new Product(id, name, info, status, price, imageURL, category);
    }
}

package by.epam.project.builder;

import by.epam.project.entity.Category;
import by.epam.project.entity.Product;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategoryBuilder {
    private Long id;
    private String name;
    private Set<Product> products = new HashSet<>();

    public CategoryBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public Category build() {
        return new Category(id, name, products);
    }
}

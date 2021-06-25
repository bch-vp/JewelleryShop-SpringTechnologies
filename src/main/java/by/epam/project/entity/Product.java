package by.epam.project.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The type Product.
 */
@Entity
@Table(name = "products")
public class Product {
    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Active status.
         */
        ACTIVE,
        /**
         * Inactive status.
         */
        INACTIVE,
        /**
         * Blocked status.
         */
        BLOCKED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String info;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal price;

    @Column(name = "image_url")
    private String imageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    public Product(Long id, String name, String info, Status status, BigDecimal price, String imageURL,
                   Category category) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
        this.imageURL = imageURL;
        this.category = category;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets image url.
     *
     * @return the image url
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Sets image url.
     *
     * @param imageURL the image url
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product user = (Product) o;

        if (id != user.id) {
            return false;
        }

        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        if (info != null ? !info.equals(user.info) : user.info != null) {
            return false;
        }
        if (status != null ? !status.equals(user.status) : user.status != null) {
            return false;
        }
        if (price != null ? !price.equals(user.price) : user.price != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id='").append(id).append('\'');
        sb.append("name='").append(name).append('\'');
        sb.append(", info='").append(info).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}

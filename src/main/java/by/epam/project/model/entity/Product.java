package by.epam.project.model.entity;

import java.math.BigDecimal;

/**
 * The type Product.
 */
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

    private long id;
    private String name;
    private String info;
    private Status status;
    private BigDecimal price;
    private String imageURL;

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    /**
     * Instantiates a new Product.
     *
     * @param id     the id
     * @param name   the name
     * @param info   the info
     * @param status the status
     * @param price  the price
     */
    public Product(long id, String name, String info, Status status, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
    }

    /**
     * Instantiates a new Product.
     *
     * @param id       the id
     * @param name     the name
     * @param info     the info
     * @param status   the status
     * @param price    the price
     * @param imageURL the image url
     */
    public Product(long id, String name, String info, Status status, BigDecimal price, String imageURL) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
        this.imageURL = imageURL;
    }

    /**
     * Instantiates a new Product.
     *
     * @param name     the name
     * @param info     the info
     * @param status   the status
     * @param price    the price
     * @param imageURL the image url
     */
    public Product(String name, String info, Status status, BigDecimal price, String imageURL) {
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
        this.imageURL = imageURL;
    }

    /**
     * Instantiates a new Product.
     *
     * @param name   the name
     * @param info   the info
     * @param status the status
     * @param price  the price
     */
    public Product(String name, String info, Status status, BigDecimal price) {
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
    }

    /**
     * Instantiates a new Product.
     *
     * @param id    the id
     * @param name  the name
     * @param info  the info
     * @param price the price
     */
    public Product(long id, String name, String info, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.price = price;
    }

    /**
     * Instantiates a new Product.
     *
     * @param name  the name
     * @param info  the info
     * @param price the price
     */
    public Product(String name, String info, BigDecimal price) {
        this.name = name;
        this.info = info;
        this.price = price;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
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

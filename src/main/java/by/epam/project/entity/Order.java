package by.epam.project.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Order.
 */
@Entity
@Table(name = "orders")
public class Order {
    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Confirmed status.
         */
        CONFIRMED,
        /**
         * Not confirmed status.
         */
        NOT_CONFIRMED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String comment;

    private String address;

    @Column(name = "time_created")
    private Date timeCreated;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    private Set<Product> products = new HashSet<>();

    public Order() {

    }

    /**
     * Instantiates a new Order.
     *
     * @param id            the id
     * @param comment       the comment
     * @param address       the address
     * @param dateCreatedAt the date created at
     * @param totalPrice    the total price
     * @param status        the status
     */
    public Order(long id, String comment, String address, Date dateCreatedAt, BigDecimal totalPrice, Status status) {
        this.id = id;
        this.comment = comment;
        this.address = address;
        this.timeCreated = dateCreatedAt;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    /**
     * Instantiates a new Order.
     *
     * @param id         the id
     * @param comment    the comment
     * @param address    the address
     * @param createdAt  the created at
     * @param totalPrice the total price
     */
    public Order(long id, String comment, String address, Date createdAt, BigDecimal totalPrice) {
        this.id = id;
        this.comment = comment;
        this.address = address;
        this.timeCreated = createdAt;
        this.totalPrice = totalPrice;
        this.status = Status.NOT_CONFIRMED;
    }

    /**
     * Instantiates a new Order.
     *
     * @param comment    the comment
     * @param address    the address
     * @param createdAt  the created at
     * @param totalPrice the total price
     */
    public Order(String comment, String address, Date createdAt, BigDecimal totalPrice) {
        this.comment = comment;
        this.address = address;
        this.timeCreated = createdAt;
        this.totalPrice = totalPrice;
        this.status = Status.NOT_CONFIRMED;
    }

    /**
     * Instantiates a new Order.
     *
     * @param comment    the comment
     * @param address    the address
     * @param createdAt  the created at
     * @param totalPrice the total price
     * @param status     the status
     */
    public Order(String comment, String address, Date createdAt, BigDecimal totalPrice, Status status) {
        this.comment = comment;
        this.address = address;
        this.timeCreated = createdAt;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    /**
     * Instantiates a new Order.
     *
     * @param comment   the comment
     * @param address   the address
     * @param createdAt the created at
     */
    public Order(String comment, String address, Date createdAt) {
        this.comment = comment;
        this.address = address;
        this.timeCreated = createdAt;
    }

    /**
     * Instantiates a new Order.
     *
     * @param comment   the comment
     * @param address   the address
     * @param createdAt the created at
     * @param status    the status
     */
    public Order(String comment, String address, Date createdAt, Status status) {
        this.comment = comment;
        this.address = address;
        this.timeCreated = createdAt;
        this.status = status;
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
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets date created at.
     *
     * @return the date created at
     */
    public Date getTimeCreated() {
        return timeCreated;
    }

    /**
     * Sets date created at.
     *
     * @param dateCreatedAt the date created at
     */
    public void setTimeCreated(Date dateCreatedAt) {
        this.timeCreated = dateCreatedAt;
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
     * Gets total price.
     *
     * @return the total price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets total price.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order user = (Order) o;

        if (id != user.id) {
            return false;
        }

        if (comment != null ? !comment.equals(user.comment) : user.comment != null) {
            return false;
        }
        if (address != null ? !address.equals(user.address) : user.address != null) {
            return false;
        }
        if (timeCreated != null ? !timeCreated.equals(user.timeCreated) : user.timeCreated != null) {
            return false;
        }
        if (totalPrice != null ? !totalPrice.equals(user.totalPrice) : user.totalPrice != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (timeCreated != null ? timeCreated.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id='").append(id).append('\'');
        sb.append("comment='").append(comment).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", createdAt='").append(timeCreated).append('\'');
        sb.append(", totalPrice=").append(totalPrice);
        sb.append('}');
        return sb.toString();
    }
}

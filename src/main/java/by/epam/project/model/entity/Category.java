package by.epam.project.model.entity;

/**
 * The type Category.
 */
public class Category {
    private long id;
    private String name;

    /**
     * Instantiates a new Category.
     */
    public Category() {
    }

    /**
     * Instantiates a new Category.
     *
     * @param id   the id
     * @param name the name
     */
    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Instantiates a new Category.
     *
     * @param name the name
     */
    public Category(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Category user = (Category) o;

        if (id != user.id) {
            return false;
        }

        if (name != null ? !name.equals(user.name) : user.name != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("id='").append(id).append('\'');
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

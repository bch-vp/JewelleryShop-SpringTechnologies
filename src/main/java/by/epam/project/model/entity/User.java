package by.epam.project.model.entity;

/**
 * The type User.
 */
public class User {

    /**
     * The enum Role.
     */
    public enum Role {
        /**
         * Guest role.
         */
        GUEST,
        /**
         * Client role.
         */
        CLIENT,
        /**
         * Admin role.
         */
        ADMIN
    }

    /**
     * The enum Status.
     */
    public enum Status {
        /**
         * Activated status.
         */
        ACTIVATED,
        /**
         * Not activated status.
         */
        NOT_ACTIVATED,
        /**
         * Banned status.
         */
        BANNED
    }

    private long id;
    private String login;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private Role role;
    private Status status;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id              the id
     * @param login           the login
     * @param firstName       the first name
     * @param lastName        the last name
     * @param telephoneNumber the telephone number
     * @param email           the email
     * @param role            the role
     * @param status          the status
     */
    public User(long id, String login, String firstName, String lastName, String telephoneNumber, String email,
                Role role, Status status) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    /**
     * Instantiates a new User.
     *
     * @param login   the login
     * @param name    the name
     * @param surname the surname
     * @param phone   the phone
     * @param email   the email
     * @param role    the role
     * @param status  the status
     */
    public User(String login, String name, String surname, String phone, String email, Role role, Status status) {
        this.login = login;
        this.firstName = name;
        this.lastName = surname;
        this.telephoneNumber = phone;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    /**
     * Instantiates a new User.
     *
     * @param login           the login
     * @param firstName       the first name
     * @param lastName        the last name
     * @param telephoneNumber the telephone number
     * @param email           the email
     */
    public User(String login, String firstName, String lastName, String telephoneNumber, String email) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    /**
     * Instantiates a new User.
     *
     * @param login  the login
     * @param status the status
     * @param role   the role
     */
    public User(String login, Status status, Role role) {
        this.login = login;
        this.status = status;
        this.role = role;
    }

    /**
     * Instantiates a new User.
     *
     * @param login the login
     */
    public User(String login) {
        this.login = login;
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
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets telephone number.
     *
     * @return the telephone number
     */
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * Sets telephone number.
     *
     * @param telephoneNumber the telephone number
     */
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (id != user.id) {
            return false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return false;
        }
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
            return false;
        }
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
            return false;
        }
        if (telephoneNumber != null ? !telephoneNumber.equals(user.telephoneNumber) : user.telephoneNumber != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (role != null ? !role.equals(user.role) : user.role != null) {
            return false;
        }
        if (status != null ? !status.equals(user.status) : user.status != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (telephoneNumber != null ? telephoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id='").append(id).append('\'');
        sb.append("login='").append(login).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", telephoneNumber='").append(telephoneNumber).append('\'');
        sb.append(", email=").append(email);
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
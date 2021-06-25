package by.epam.project.builder;

import by.epam.project.entity.User;
import org.springframework.stereotype.Component;

public class UserBuilder {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String avatarUrl;
    private User.Role role;
    private User.Status status;

    public UserBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public UserBuilder setRole(User.Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder setStatus(User.Status status) {
        this.status = status;
        return this;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public User build() {
        return new User(id, login, firstName, lastName, telephoneNumber, email, avatarUrl, role, status);
    }
}

package by.epam.project.security;

import com.mysql.cj.xdevapi.Client;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum ApplicationUserRole {
    GUEST(Set.of(
            "\\/do\\?command=passing_by_guest",
            "\\/ao\\?command=change_language",
            "\\/ajax\\?command=check_login_existence",
            "\\/ajax\\?command=load_all_products_by_category",
            "\\/ajax\\?command=load_all_categories",
            "\\/ajax\\?command=sign_in",
            "\\/ajax\\?command=sign_up",
            "\\/do\\?command=confirm_sign_up",
            "\\/ajax\\?command=change_password_by_email"
    )),
    CLIENT(Set.of(
            "\\/ajax\\?command=add_product_to_shopping_cart",
            "\\/do\\?command=change_language",
                   "\\/ajax\\?command=change_password_by_old_password",
                   "\\/ajax\\?command=create_order",
                   "\\/ajax\\?command=load_all_categories",
                   "\\/ajax\\?command=load_all_orders",
                   "\\/ajax\\?command=load_all_products_by_category",
                   "\\/ajax\\?command=load_profile_image",
                   "\\/ajax\\?command=load_shopping_cart",
                   "\\/do\\?command=passing_by_client",
                   "\\/ajax\\?command=remove_product_from_shopping_cart",
                   "\\/ajax\\?command=remove_profile_image",
                   "\\/ajax\\?command=sign_out",
                   "\\/ajax\\?command=update_profile",
                   "\\/ajax\\?command=upload_profile_image"
    )),
    ADMIN(Set.of(
            "\\/do\\?command=change_language",
            "\\/ajax\\?command=change_password_by_old_password",
            "\\/ajax\\?command=create_category",
            "\\/ajax\\?command=create_product",
            "\\/ajax\\?command=load_all_categories",
            "\\/ajax\\?command=load_all_clients",
            "\\/ajax\\?command=load_all_orders",
            "\\/ajax\\?command=load_all_products_by_category",
            "\\/ajax\\?command=load_profile_image",
            "\\/do\\?command=passing_by_admin",
            "\\/ajax\\?command=remove_category",
            "\\/ajax\\?command=remove_profile_image",
            "\\/do\\?command=sign_out",
            "\\/ajax\\?command=update_category_name",
            "\\/ajax\\?command=update_client_status",
            "\\/ajax\\?command=update_order_status",
            "\\/ajax\\?command=update_product_category",
            "\\/ajax\\?command=update_product_info",
            "\\/ajax\\?command=update_product_status",
            "\\/ajax\\?command=update_profile",
            "\\/ajax\\?command=upload_product_image",
            "\\/ajax\\?command=upload_profile_image"
    ));

    private final Set<String> permissions;

    ApplicationUserRole(Set<String> permissions){
        this.permissions = permissions;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}

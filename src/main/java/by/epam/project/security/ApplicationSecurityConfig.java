package by.epam.project.security;

import by.epam.project.config.JwtConfig;
import by.epam.project.controller.filter.JwtTokenVerifier;
import by.epam.project.controller.filter.JwtUsernameAndPasswordAuthenticationFilter;
import by.epam.project.controller.filter.typerole.RolePermission;
import by.epam.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userServiceImpl;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public ApplicationSecurityConfig(DataSource dataSource,
                                     PasswordEncoder passwordEncoder,
                                     UserServiceImpl userServiceImpl,
                                     SecretKey secretKey,
                                     JwtConfig jwtConfig) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.userServiceImpl = userServiceImpl;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] adminUrls = ApplicationUserRole.ADMIN.getPermissions().toArray(String[]::new);
        String[] clientUrls = ApplicationUserRole.CLIENT.getPermissions().toArray(String[]::new);
        String[] guestUrls = ApplicationUserRole.GUEST.getPermissions().toArray(String[]::new);

        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/",
                        "index",
                        "/css/*",
                        "/js/**").permitAll()
                .regexMatchers(guestUrls).permitAll()
                .regexMatchers(adminUrls).hasRole(RolePermission.ADMIN.name())
                .regexMatchers(clientUrls).hasRole(RolePermission.CLIENT.name())
                .anyRequest()
                .permitAll();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter(authenticationManager());
        filter.setUsernameParameter("login");
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }

}

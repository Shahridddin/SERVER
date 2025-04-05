package uz.pdp.simple_l.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uz.pdp.simple_l.entity.AuthUser;
import uz.pdp.simple_l.repository.AuthUserRepository;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthUserRepository authUserRepository;
    private final CustomAuthenticationSuccessHandler successHandler;

    private static final String[] PUBLIC_PAGES = {
            "/auth/login",
            "/file/upload",
            "/auth/check-login",
            "/auth/register",
            "/auth/create",
            "/auth/password-reset-page",
            "/password/reset",
            "/send/message",
            "/password/changed",
            "/new/password",
            "/auth/logout-page",
            "/auth/logout-all",
            "/auth/logout",
            "/css/**",
            "/js/**",
            "/current/user"
    };

    private static final String[] ADMIN_PAGES = {
            "/admin/page-get",
            "/auth/active/users",
            "/auth/active/block/{id}",
            "/auth/active/delete/{id}",
            "/auth/inactive/users",
            "/auth/inactive/activate/{id}",
            "/auth/inactive/{id}",
            "/category/get/page",
            "/create/category",
            "/category/get-list",
            "/auth/category/delete/{id}",
            "/auth/category/add/{id}",
            "/auth/category/{id}/save-book",
            "/auth/category/add",
            "/auth/book/add/page",
    };

    public SecurityConfiguration(AuthUserRepository authUserRepository, CustomAuthenticationSuccessHandler successHandler) {
        this.authUserRepository = authUserRepository;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Hamma uchun ochiq sahifalar
                        .requestMatchers(PUBLIC_PAGES).permitAll()
                        // User uchun ruxsat berilgan sahifalar
                        .requestMatchers("/user/page-get").hasRole("USER")

                        // Admin uchun ruxsat berilgan sahifalar
                        .requestMatchers(ADMIN_PAGES).hasRole("ADMIN")

                        // Barcha boshqa so‘rovlar autentifikatsiyani talab qiladi
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(successHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService customUserDetails() {
        return username -> {
            Optional<AuthUser> byUsername = authUserRepository.findByUsername(username);
            if (byUsername.isEmpty()) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            AuthUser authUser = byUsername.get();

            return new CustomUserDetails(
                    authUser.getUsername(),
                    authUser.getPassword(),
                    authUser.getEmail(),
                    authUser.isActive(),
                    authUser.getRole()
            );
        };
    }
}

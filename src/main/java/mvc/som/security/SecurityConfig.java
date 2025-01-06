package mvc.som.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.List;

@Configuration
public class SecurityConfig {

    private static final List<String> ROLE_HIERARCHY = List.of("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER");

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(config ->
                        config
                                .requestMatchers("/customer/delete").hasRole("MANAGER")
                                .requestMatchers("/product/delete").hasRole("MANAGER")
                                .requestMatchers("/order/delete").hasRole("MANAGER")
                                .anyRequest().authenticated())
                .formLogin(FormLoginConfigurer::permitAll)
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(config ->
                        config
                                .accessDeniedPage("/access-denied"));
        return httpSecurity.build();
    }

    public String getHighestRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .min(Comparator.comparingInt(ROLE_HIERARCHY::indexOf))
                .orElse("ROLE_USER");
    }

}

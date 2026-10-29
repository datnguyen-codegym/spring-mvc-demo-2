package codegym.vn.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * DaoAuthenticationProvider dùng UserDetailsService (bản @Primary -
     * {@link SpringUserDetailsService}) + PasswordEncoder (BCrypt).
     *
     * Để dùng bản class riêng ({@link CustomUserDetailsService}): đổi @Primary
     * giữa hai service, hoặc inject theo tên bean cụ thể tại đây.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            @Qualifier(value = "customUserDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            DaoAuthenticationProvider daoAuthenticationProvider) {
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {

        http
                .authenticationProvider(daoAuthenticationProvider)
                .authorizeRequests().antMatchers("/customer/**").hasAuthority("USER_ROLE").and()
                .authorizeRequests().anyRequest().authenticated().and()
                // HTTP Basic cho API stateless.
                .httpBasic().and()
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Tắt form login: chỉ dùng HTTP Basic.
                .formLogin().disable()
                // Tắt CSRF: API stateless dùng HTTP Basic, không dùng session cookie cho form.
                .csrf().disable();

        return http.build();
    }
}

package rea.yooolio.attestationspringapp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import rea.yooolio.attestationspringapp.services.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
                    authorizeHttpRequestsCustomizer.requestMatchers("/error").permitAll();
                    authorizeHttpRequestsCustomizer.requestMatchers("/auth/login", "/auth/register").anonymous();
                    authorizeHttpRequestsCustomizer.requestMatchers("/products/add", "/products/*/edit", "/admin/**").hasRole("ADMIN");
                    authorizeHttpRequestsCustomizer.requestMatchers("/cart", "/orders").hasAnyRole("USER", "ADMIN");
                    authorizeHttpRequestsCustomizer.anyRequest().permitAll();
                })
                .formLogin(formLoginCustomizer -> {
                    formLoginCustomizer.loginPage("/auth/login");
                    formLoginCustomizer.loginProcessingUrl("/login");
                    formLoginCustomizer.failureUrl("/auth/login");
                    formLoginCustomizer.defaultSuccessUrl("/", true);
                })
                .logout(logoutCustomizer -> {
                    logoutCustomizer.logoutUrl("/logout");
                    logoutCustomizer.logoutSuccessUrl("/");
                })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

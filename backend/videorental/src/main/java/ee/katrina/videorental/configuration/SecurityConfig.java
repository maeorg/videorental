package ee.katrina.videorental.configuration;

import ee.katrina.videorental.security.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    TokenParser tokenParser;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors().and().headers().xssProtection().disable().and()
                .csrf().disable()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(antMatcher("/login")).permitAll()
                        .requestMatchers(antMatcher("/signup")).hasAuthority("admin")
                        .requestMatchers(antMatcher("/api/v1/movie/available")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/rental")).hasAnyAuthority("employee", "admin")
                        .requestMatchers(antMatcher("/api/v1/return")).hasAnyAuthority("employee", "admin")
                        .requestMatchers(antMatcher("/api/v1/customer")).hasAnyAuthority("employee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.POST.toString())).hasAnyAuthority("employee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.DELETE.toString())).hasAnyAuthority("employee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.PUT.toString())).hasAnyAuthority("employee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.PATCH.toString())).hasAnyAuthority("employee", "admin")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenParser, BasicAuthenticationFilter.class)
                .build();
    }
}

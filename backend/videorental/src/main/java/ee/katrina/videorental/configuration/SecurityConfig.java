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
                        .requestMatchers(antMatcher("/signup")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/movie")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/movie/available")).permitAll()
                        .requestMatchers(antMatcher("/api/v1/rental")).hasAuthority("admin")
                        .requestMatchers(antMatcher("/api/v1/return")).hasAuthority("admin")
                        .requestMatchers(antMatcher("/api/v1/customer")).hasAuthority("admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.GET.toString())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.POST.toString())).hasAuthority("admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.DELETE.toString())).hasAuthority("admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.PUT.toString())).hasAuthority("admin")
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/movie",
                                HttpMethod.PATCH.toString())).hasAuthority("admin")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenParser, BasicAuthenticationFilter.class)
                .build();
    }
}

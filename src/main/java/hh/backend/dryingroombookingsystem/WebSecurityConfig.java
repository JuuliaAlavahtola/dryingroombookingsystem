package hh.backend.dryingroombookingsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.boot.security.autoconfigure.web.servlet.PathRequest.toH2Console;

import hh.backend.dryingroombookingsystem.web.UserDetailServiceImp;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private final UserDetailServiceImp userDetailsService;

    public WebSecurityConfig(UserDetailServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/bookings", "/resident/list", "/rooms", "/css/**", "/api/booking/**")
                        .permitAll()
                        .requestMatchers("/add", "/save")
                        .hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/edit/**", "/delete/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers(toH2Console())
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(toH2Console()))
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()))
                .formLogin(form -> form
                        .defaultSuccessUrl("/bookings", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll())
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

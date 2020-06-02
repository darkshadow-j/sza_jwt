package pl.plenczewski.homejwt.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/books").hasRole("ADMIN")
                .and().addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
    }
}

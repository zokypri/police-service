package se.implementer.policeservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    protected static final List<String> DEFAULT_EXCLUDE_URL_PATTERNS = List.of("/v3/api-docs/**", "/swagger-resources/**",
            "/swagger-ui.html", "/swagger-ui/**");

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/**").permitAll() //TODO close this when JWT is implemented
                .and()
                .authorizeRequests()
                .antMatchers(DEFAULT_EXCLUDE_URL_PATTERNS.toArray(new String[0]))
                .permitAll()
                .and().authorizeRequests()
                .anyRequest().authenticated();
    }
}

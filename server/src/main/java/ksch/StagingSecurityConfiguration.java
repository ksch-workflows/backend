package ksch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("staging")
public class StagingSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest()
            .authenticated()
        ;
        http.oauth2ResourceServer().jwt();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}

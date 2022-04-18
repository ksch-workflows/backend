package ksch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.server.MatcherSecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import ksch.bff.TokenFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenFilter tokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.addFilterBefore(tokenFilter, BearerTokenAuthenticationFilter.class);
        http
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/registration-desk/**").permitAll()
            .antMatchers("/bff/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest()
            .authenticated()
        ;
        http.oauth2ResourceServer().opaqueToken();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // http.sessionManagement().sessionFixation().none();
    }
}

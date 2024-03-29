/*
 * Copyright 2021 KS-plus e.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ksch;

import ksch.bff.TokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;

import static org.springframework.http.HttpMethod.OPTIONS;

/**
 * <h3>References</h3>
 * <ul>
 *     <li>
 *         <a href="https://www.baeldung.com/spring-security-whitelist-ip-range">
 *             Spring Security – Whitelist IP Range | baeldung.com
*          </a>
 *     </li>
 * </ul>
 */
@Configuration
@Profile("dev")
@RequiredArgsConstructor
@SuppressWarnings("squid:S4502") // CSRF handling will be tackled with https://github.com/ksch-workflows/backend/issues/51
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOCALHOST = "127.0.0.1";

    private final TokenFilter tokenFilter;

    @SuppressWarnings("squid:S4601") // IP address check has to be done first
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.addFilterBefore(tokenFilter, BearerTokenAuthenticationFilter.class);
        http
            .authorizeRequests()
            .antMatchers("**").hasIpAddress(LOCALHOST)
            .antMatchers(OPTIONS, "/api/**").permitAll()
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
    }
}

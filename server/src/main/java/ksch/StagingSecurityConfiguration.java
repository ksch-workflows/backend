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

@Configuration
@Profile("staging")
@SuppressWarnings("squid:S4502") // CSRF handling will be tackled with https://github.com/ksch-workflows/backend/issues/51
@RequiredArgsConstructor
public class StagingSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenFilter tokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenFilter, BearerTokenAuthenticationFilter.class);
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/registration-desk/**").permitAll()
                .antMatchers("/bff/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated();
        http.oauth2ResourceServer().jwt();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}

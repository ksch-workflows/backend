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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import ksch.testing.PostgresTestConfiguration;

public class BackendApplicationWithDevServices {

    static class ContainerConfig {
        @Bean
        @ServiceConnection
        public PostgreSQLContainer<?> postgresContainer() {
            //noinspection resource
            return new PostgreSQLContainer<>(
                "postgres:15-alpine"
            ).withReuse(true);
        }
    }

    public static void main(String... args) {
        SpringApplication.from(BackendApplication::main)
            .with(PostgresTestConfiguration.class)
            .run(args);
    }
}
package ksch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "ksch")
@EntityScan("ksch")
@EnableJpaRepositories("ksch")
public class BackendApplication {

  public static void main(String... args) {

    Manager m = null;

    SpringApplication.run(BackendApplication.class, args);
  }
}
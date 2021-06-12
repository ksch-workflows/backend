package ksch;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Allow all CORS requests during development. This configuration must not be applied for production deployments.
 *
 * @see <a href="https://spring.io/blog/2015/06/08/cors-support-in-spring-framework">spring.io</a>
 */
@Configuration
public class DevelopmentCorsPolicy implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}

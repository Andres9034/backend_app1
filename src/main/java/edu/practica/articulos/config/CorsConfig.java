package edu.practica.articulos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS.
 * Para qué sirve: el frontend (HTML/JS) y el backend (Spring Boot) van
 * en carpetas / servidores distintos. El navegador bloquea eso si no
 * hay CORS. Aquí se permite que el JS llame a /api/**.
 *
 * Cuando se usen dos VMs (Ubuntu + CentOS), no hay que reescribir el CRUD:
 * solo se cambia la IP en el JS. Esta config ya acepta cualquier origen.
 */
@Configuration
public class CorsConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOriginPatterns("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*");
			}
		};
	}
}

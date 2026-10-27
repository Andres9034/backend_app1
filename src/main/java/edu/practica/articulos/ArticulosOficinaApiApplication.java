package edu.practica.articulos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase de arranque de Spring Boot.
 * Para qué sirve: es el "main" del backend. Al ejecutarla se levantan
 * el servidor Tomcat embebido (puerto 8080), JPA y el resto de beans.
 *
 * @SpringBootApplication junta:
 *  - @Configuration (esta clase puede definir beans)
 *  - @EnableAutoConfiguration (Spring configura Web, JPA, H2 solo)
 *  - @ComponentScan (busca @RestController, @Service, etc. en este paquete)
 */
@SpringBootApplication
public class ArticulosOficinaApiApplication {

	/**
	 * Punto de entrada de la JVM.
	 * args son argumentos de consola (casi nunca se usan en esta práctica).
	 */
	public static void main(String[] args) {
		SpringApplication.run(ArticulosOficinaApiApplication.class, args);
	}

}

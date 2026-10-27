package edu.practica.articulos.config;

import edu.practica.articulos.modelo.Articulo;
import edu.practica.articulos.repositorio.ArticuloRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Semilla de datos.
 * Para qué sirve: si la tabla está vacía al arrancar, inserta artículos
 * de oficina de ejemplo para que el frontend tenga algo que pintar
 * (la profesora pidió una lista de artículos de oficina).
 *
 * CommandLineRunner se ejecuta UNA vez cuando Spring ya arrancó.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

	private final ArticuloRepositorio repositorio;

	public DatosIniciales(ArticuloRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public void run(String... args) {
		if (repositorio.count() > 0) {
			return;
		}

		repositorio.save(new Articulo("Bolígrafo azul", "Escritura", 40, 1500.0));
		repositorio.save(new Articulo("Resma de papel carta", "Papelería", 12, 18500.0));
		repositorio.save(new Articulo("Grapadora metálica", "Organización", 8, 22000.0));
		repositorio.save(new Articulo("Carpeta oficio", "Organización", 25, 3500.0));
		repositorio.save(new Articulo("Caja de clips", "Organización", 30, 2500.0));
		repositorio.save(new Articulo("Tijeras de oficina", "Herramientas", 10, 8900.0));
	}
}

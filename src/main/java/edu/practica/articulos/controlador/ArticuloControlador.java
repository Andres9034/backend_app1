package edu.practica.articulos.controlador;

import edu.practica.articulos.modelo.Articulo;
import edu.practica.articulos.servicio.ArticuloServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST de artículos.
 * Para qué sirve: es la CAPA DE PRESENTACIÓN del backend (la API).
 * Recibe HTTP del frontend (HTML/JS en otra carpeta o, más adelante, otra VM)
 * y responde JSON. No pinta pantallas: solo JSON.
 *
 * Ruta base: /api/articulos
 */
@RestController
@RequestMapping("/api/articulos")
public class ArticuloControlador {

	private final ArticuloServicio servicio;

	public ArticuloControlador(ArticuloServicio servicio) {
		this.servicio = servicio;
	}

	/**
	 * GET /api/articulos
	 * Para qué sirve: el frontend pide la lista y la pinta en la tabla.
	 */
	@GetMapping
	public List<Articulo> listar() {
		return servicio.listar();
	}

	/**
	 * GET /api/articulos/{id}
	 * Para qué sirve: traer un solo artículo (por si se necesita editar).
	 */
	@GetMapping("/{id}")
	public Articulo obtener(@PathVariable Long id) {
		return servicio.obtenerPorId(id);
	}

	/**
	 * POST /api/articulos
	 * Cuerpo JSON: { "nombre", "categoria", "cantidad", "precio" }
	 * @Valid activa las anotaciones del modelo (@NotBlank, @Min, etc.).
	 */
	@PostMapping
	public ResponseEntity<Articulo> crear(@Valid @RequestBody Articulo articulo) {
		Articulo creado = servicio.crear(articulo);
		return ResponseEntity.status(HttpStatus.CREATED).body(creado);
	}

	/**
	 * PUT /api/articulos/{id}
	 * Para qué sirve: reemplazar un artículo existente (CRUD: Update).
	 */
	@PutMapping("/{id}")
	public Articulo actualizar(@PathVariable Long id, @Valid @RequestBody Articulo articulo) {
		return servicio.actualizar(id, articulo);
	}

	/**
	 * DELETE /api/articulos/{id}
	 * Para qué sirve: borrar un artículo (CRUD: Delete).
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		servicio.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}

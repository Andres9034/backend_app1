package edu.practica.articulos.servicio;

import edu.practica.articulos.excepcion.RecursoNoEncontradoException;
import edu.practica.articulos.modelo.Articulo;
import edu.practica.articulos.repositorio.ArticuloRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de artículos.
 * Para qué sirve: es la LÓGICA DE NEGOCIO. El controlador no habla con
 * la base de datos directo: llama a esta clase. Así las capas quedan
 * desacopladas (presentación / negocio / datos), como pide la práctica.
 */
@Service
public class ArticuloServicio {

	/** Inyección por constructor: Spring pasa el repositorio solo. */
	private final ArticuloRepositorio repositorio;

	public ArticuloServicio(ArticuloRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	/** GET: lista todos los artículos de oficina. */
	public List<Articulo> listar() {
		return repositorio.findAll();
	}

	/** GET por id. Si no existe, lanza 404 (lo atrapa el manejador global). */
	public Articulo obtenerPorId(Long id) {
		return repositorio.findById(id)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"No existe el artículo con id " + id));
	}

	/** POST: crea un artículo nuevo. El id lo pone la base de datos. */
	public Articulo crear(Articulo articulo) {
		articulo.setId(null);
		return repositorio.save(articulo);
	}

	/** PUT: reemplaza los campos de un artículo que ya existe. */
	public Articulo actualizar(Long id, Articulo datos) {
		Articulo existente = obtenerPorId(id);
		existente.setNombre(datos.getNombre());
		existente.setCategoria(datos.getCategoria());
		existente.setCantidad(datos.getCantidad());
		existente.setPrecio(datos.getPrecio());
		return repositorio.save(existente);
	}

	/** DELETE: borra el artículo si existe. */
	public void eliminar(Long id) {
		Articulo existente = obtenerPorId(id);
		repositorio.delete(existente);
	}
}

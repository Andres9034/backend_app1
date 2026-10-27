package edu.practica.articulos.excepcion;

/**
 * Excepción de negocio para "no encontré ese id".
 * Para qué sirve: el servicio la lanza y el manejador global la convierte
 * en HTTP 404 con un JSON claro para el frontend.
 */
public class RecursoNoEncontradoException extends RuntimeException {

	public RecursoNoEncontradoException(String mensaje) {
		super(mensaje);
	}
}

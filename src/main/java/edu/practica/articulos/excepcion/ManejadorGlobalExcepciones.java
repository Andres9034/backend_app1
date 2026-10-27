package edu.practica.articulos.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de errores.
 * Para qué sirve: evita que al frontend le llegue un HTML feo de Spring.
 * Siempre devolvemos JSON con "error" y, si aplica, "detalles".
 */
@RestControllerAdvice
public class ManejadorGlobalExcepciones {

	/** 404: el id no existe en la tabla articulos. */
	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<Map<String, Object>> noEncontrado(RecursoNoEncontradoException ex) {
		return respuesta(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	/** 400: falló @Valid (nombre vacío, precio 0, etc.). */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> validacion(MethodArgumentNotValidException ex) {
		Map<String, String> detalles = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> detalles.put(error.getField(), error.getDefaultMessage()));

		Map<String, Object> cuerpo = new HashMap<>();
		cuerpo.put("error", "Datos inválidos");
		cuerpo.put("detalles", detalles);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cuerpo);
	}

	private ResponseEntity<Map<String, Object>> respuesta(HttpStatus estado, String mensaje) {
		Map<String, Object> cuerpo = new HashMap<>();
		cuerpo.put("error", mensaje);
		return ResponseEntity.status(estado).body(cuerpo);
	}
}

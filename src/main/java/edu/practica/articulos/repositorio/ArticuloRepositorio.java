package edu.practica.articulos.repositorio;

import edu.practica.articulos.modelo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de artículos.
 * Para qué sirve: es la capa de ACCESO A DATOS. Habla con la base H2
 * sin que nosotros escribamos SQL a mano.
 *
 * JpaRepository ya trae:
 *  - findAll()          → SELECT * FROM articulos
 *  - findById(id)       → SELECT por id
 *  - save(articulo)     → INSERT o UPDATE
 *  - deleteById(id)     → DELETE
 *  - count()            → cuántas filas hay
 *
 * @Repository marca esta interfaz como componente de Spring.
 */
@Repository
public interface ArticuloRepositorio extends JpaRepository<Articulo, Long> {
	// No hace falta escribir métodos. Si más adelante se busca por nombre:
	// List<Articulo> findByNombreContainingIgnoreCase(String nombre);
}

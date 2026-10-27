package edu.practica.articulos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad Articulo.
 * Para qué sirve: representa UNA fila de la tabla articulos.
 * JPA convierte esta clase Java en SQL (CREATE TABLE / INSERT / UPDATE).
 *
 * Es el modelo de negocio de la práctica: artículos de oficina.
 */
@Entity
@Table(name = "articulos")
public class Articulo {

	/**
	 * Clave primaria. La genera sola la base de datos (IDENTITY = autoincrement).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre del artículo (ej: "Bolígrafo azul").
	 * @NotBlank evita null o solo espacios.
	 */
	@NotBlank(message = "El nombre es obligatorio")
	@Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres")
	@Column(nullable = false, length = 80)
	private String nombre;

	/**
	 * Categoría para agrupar (Papelería, Escritura, Organización, etc.).
	 */
	@NotBlank(message = "La categoría es obligatoria")
	@Size(min = 2, max = 40, message = "La categoría debe tener entre 2 y 40 caracteres")
	@Column(nullable = false, length = 40)
	private String categoria;

	/**
	 * Unidades en inventario. No puede ser negativa.
	 */
	@Min(value = 0, message = "La cantidad no puede ser negativa")
	@Column(nullable = false)
	private Integer cantidad;

	/**
	 * Precio unitario. Debe ser mayor que 0.
	 */
	@DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
	@Column(nullable = false)
	private Double precio;

	/** Constructor vacío: JPA lo necesita para crear objetos desde la BD. */
	public Articulo() {
	}

	/** Constructor con datos: se usa al sembrar artículos de ejemplo. */
	public Articulo(String nombre, String categoria, Integer cantidad, Double precio) {
		this.nombre = nombre;
		this.categoria = categoria;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	// Getters y setters: Spring / Jackson los usa para leer y escribir JSON.

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}

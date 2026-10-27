package edu.practica.articulos;

import edu.practica.articulos.modelo.Articulo;
import edu.practica.articulos.repositorio.ArticuloRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Prueba del CRUD HTTP.
 * Para qué sirve: comprueba que GET/POST/PUT/DELETE responden bien
 * sin abrir el navegador.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ArticuloControladorTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ArticuloRepositorio repositorio;

	@BeforeEach
	void limpiarYSembrar() {
		repositorio.deleteAll();
		repositorio.save(new Articulo("Lápiz HB", "Escritura", 5, 800.0));
	}

	@Test
	void listarDevuelveOk() throws Exception {
		mockMvc.perform(get("/api/articulos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(1)));
	}

	@Test
	void crearDevuelve201() throws Exception {
		String json = """
				{"nombre":"Marcador negro","categoria":"Escritura","cantidad":3,"precio":4200}
				""";
		mockMvc.perform(post("/api/articulos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nombre").value("Marcador negro"));
	}

	@Test
	void actualizarDevuelveOk() throws Exception {
		Long id = repositorio.findAll().get(0).getId();
		String json = """
				{"nombre":"Lápiz 2B","categoria":"Escritura","cantidad":9,"precio":900}
				""";
		mockMvc.perform(put("/api/articulos/" + id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Lápiz 2B"));
	}

	@Test
	void eliminarDevuelve204() throws Exception {
		Long id = repositorio.findAll().get(0).getId();
		mockMvc.perform(delete("/api/articulos/" + id))
				.andExpect(status().isNoContent());
	}
}

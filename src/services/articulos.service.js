const HttpError = require("../utils/http-error");
const validarArticulo = require("../validators/articulo.validator");

class ArticulosService {
  constructor(repositorio) {
    this.repositorio = repositorio;
  }

  listar() {
    return this.repositorio.listar();
  }

  obtenerPorId(idRecibido) {
    const id = this.validarId(idRecibido);
    const articulo = this.repositorio.obtenerPorId(id);
    if (!articulo) {
      throw new HttpError(404, "No existe el artículo con id " + id);
    }
    return articulo;
  }

  crear(datos) {
    return this.repositorio.crear(validarArticulo(datos));
  }

  actualizar(idRecibido, datos) {
    const id = this.validarId(idRecibido);
    this.obtenerPorId(id);
    return this.repositorio.actualizar(id, validarArticulo(datos));
  }

  eliminar(idRecibido) {
    const articulo = this.obtenerPorId(idRecibido);
    this.repositorio.eliminar(articulo.id);
  }

  validarId(idRecibido) {
    if (!/^\d+$/.test(idRecibido)) {
      throw new HttpError(400, "El id debe ser un número entero positivo");
    }
    const id = Number(idRecibido);
    if (!Number.isSafeInteger(id) || id < 1) {
      throw new HttpError(400, "El id debe ser un número entero positivo");
    }
    return id;
  }
}

module.exports = ArticulosService;

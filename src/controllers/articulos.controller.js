const ArticulosService = require("../services/articulos.service");

class ArticulosController {
  constructor(repositorio) {
    this.servicio = new ArticulosService(repositorio);
    this.listar = this.listar.bind(this);
    this.obtener = this.obtener.bind(this);
    this.crear = this.crear.bind(this);
    this.actualizar = this.actualizar.bind(this);
    this.eliminar = this.eliminar.bind(this);
  }

  listar(request, response) {
    response.json(this.servicio.listar());
  }

  obtener(request, response) {
    response.json(this.servicio.obtenerPorId(request.params.id));
  }

  crear(request, response) {
    response.status(201).json(this.servicio.crear(request.body));
  }

  actualizar(request, response) {
    response.json(this.servicio.actualizar(request.params.id, request.body));
  }

  eliminar(request, response) {
    this.servicio.eliminar(request.params.id);
    response.status(204).end();
  }
}

module.exports = ArticulosController;

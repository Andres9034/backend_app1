const express = require("express");
const ArticulosController = require("../controllers/articulos.controller");

function crearRutasArticulos(repositorio) {
  const router = express.Router();
  const controlador = new ArticulosController(repositorio);

  router.get("/", controlador.listar);
  router.get("/:id", controlador.obtener);
  router.post("/", controlador.crear);
  router.put("/:id", controlador.actualizar);
  router.delete("/:id", controlador.eliminar);

  return router;
}

module.exports = crearRutasArticulos;

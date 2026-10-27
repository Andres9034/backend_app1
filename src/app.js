const cors = require("cors");
const express = require("express");
const path = require("node:path");
const ArticulosRepository = require("./repositories/articulos.repository");
const crearRutasArticulos = require("./routes/articulos.routes");
const manejarErrores = require("./middleware/error-handler");
const HttpError = require("./utils/http-error");

function crearApp({
  archivoDatos = process.env.DATA_FILE || path.join(__dirname, "..", "data", "articulos.json"),
} = {}) {
  const app = express();
  const repositorio = new ArticulosRepository(archivoDatos);

  app.use(cors());
  app.use(express.json({ limit: "1mb" }));
  app.use("/api/articulos", crearRutasArticulos(repositorio));
  app.use((request, response, next) => {
    next(new HttpError(404, "Ruta no encontrada"));
  });
  app.use(manejarErrores);

  return app;
}

module.exports = crearApp;

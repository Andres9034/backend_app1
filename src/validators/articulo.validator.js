const HttpError = require("../utils/http-error");

function validarArticulo(datos) {
  if (!datos || typeof datos !== "object" || Array.isArray(datos)) {
    throw new HttpError(400, "El cuerpo debe ser un objeto JSON");
  }

  const detalles = {};
  const campos = {};

  if (typeof datos.nombre !== "string" || datos.nombre.trim().length < 2 || datos.nombre.trim().length > 80) {
    detalles.nombre = "El nombre debe tener entre 2 y 80 caracteres";
  } else {
    campos.nombre = datos.nombre.trim();
  }

  if (
    typeof datos.categoria !== "string" ||
    datos.categoria.trim().length < 2 ||
    datos.categoria.trim().length > 40
  ) {
    detalles.categoria = "La categoría debe tener entre 2 y 40 caracteres";
  } else {
    campos.categoria = datos.categoria.trim();
  }

  if (!Number.isInteger(datos.cantidad) || datos.cantidad < 0) {
    detalles.cantidad = "La cantidad no puede ser negativa y debe ser un número entero";
  } else {
    campos.cantidad = datos.cantidad;
  }

  if (typeof datos.precio !== "number" || !Number.isFinite(datos.precio) || datos.precio < 0.01) {
    detalles.precio = "El precio debe ser mayor que 0";
  } else {
    campos.precio = datos.precio;
  }

  if (Object.keys(detalles).length > 0) {
    throw new HttpError(400, "Datos inválidos", detalles);
  }
  return campos;
}

module.exports = validarArticulo;

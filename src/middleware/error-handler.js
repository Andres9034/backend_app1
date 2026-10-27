function manejarErrores(error, request, response, next) {
  if (response.headersSent) {
    next(error);
    return;
  }

  if (error.type === "entity.parse.failed") {
    response.status(400).json({ error: "JSON inválido" });
    return;
  }

  if (error.type === "entity.too.large") {
    response.status(413).json({ error: "El cuerpo de la solicitud supera el límite permitido" });
    return;
  }

  if (Number.isInteger(error.status) && error.status >= 400 && error.status < 500) {
    const cuerpo = { error: error.message };
    if (error.detalles) {
      cuerpo.detalles = error.detalles;
    }
    response.status(error.status).json(cuerpo);
    return;
  }

  console.error("Error al procesar la solicitud:", error);
  response.status(500).json({ error: "Error interno del servidor" });
}

module.exports = manejarErrores;

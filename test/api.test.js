const assert = require("node:assert/strict");
const fs = require("node:fs");
const os = require("node:os");
const path = require("node:path");
const { test } = require("node:test");
const crearApp = require("../src/app");

async function iniciarApi() {
  const directorio = fs.mkdtempSync(path.join(os.tmpdir(), "articulos-api-"));
  const archivoDatos = path.join(directorio, "articulos.json");
  const servidor = crearApp({ archivoDatos }).listen(0, "127.0.0.1");
  await new Promise((resolve) => servidor.once("listening", resolve));

  return {
    baseUrl: "http://127.0.0.1:" + servidor.address().port,
    archivoDatos,
    async cerrar() {
      servidor.closeAllConnections();
      await new Promise((resolve, reject) => {
        servidor.close((error) => (error ? reject(error) : resolve()));
      });
      fs.rmSync(directorio, { recursive: true, force: true });
    },
  };
}

test("mantiene el CRUD, valida datos y persiste cambios", async () => {
  const api = await iniciarApi();
  try {
    const listaInicial = await fetch(api.baseUrl + "/api/articulos");
    assert.equal(listaInicial.status, 200);
    assert.equal((await listaInicial.json()).length, 6);

    const creado = await fetch(api.baseUrl + "/api/articulos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        nombre: "  Marcador negro ",
        categoria: " Escritura ",
        cantidad: 3,
        precio: 4200,
      }),
    });
    assert.equal(creado.status, 201);
    const articuloCreado = await creado.json();
    assert.equal(articuloCreado.id, 7);
    assert.equal(articuloCreado.nombre, "Marcador negro");
    assert.equal(articuloCreado.categoria, "Escritura");

    const obtenido = await fetch(api.baseUrl + "/api/articulos/7");
    assert.equal((await obtenido.json()).precio, 4200);

    const actualizado = await fetch(api.baseUrl + "/api/articulos/7", {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        nombre: "Marcador azul",
        categoria: "Escritura",
        cantidad: 9,
        precio: 4500,
      }),
    });
    assert.equal(actualizado.status, 200);
    assert.equal((await actualizado.json()).nombre, "Marcador azul");

    const eliminado = await fetch(api.baseUrl + "/api/articulos/7", { method: "DELETE" });
    assert.equal(eliminado.status, 204);
    assert.equal((await fetch(api.baseUrl + "/api/articulos/7")).status, 404);

    const persistido = JSON.parse(fs.readFileSync(api.archivoDatos, "utf8"));
    assert.equal(persistido.articulos.length, 6);
    assert.equal(persistido.siguienteId, 8);
  } finally {
    await api.cerrar();
  }
});

test("responde 400 a datos inválidos y JSON mal formado", async () => {
  const api = await iniciarApi();
  try {
    const invalido = await fetch(api.baseUrl + "/api/articulos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nombre: "", categoria: "A", cantidad: -1, precio: 0 }),
    });
    assert.equal(invalido.status, 400);
    const errorValidacion = await invalido.json();
    assert.equal(errorValidacion.error, "Datos inválidos");
    assert.ok(errorValidacion.detalles.nombre);
    assert.ok(errorValidacion.detalles.cantidad);
    assert.ok(errorValidacion.detalles.precio);

    const jsonMalFormado = await fetch(api.baseUrl + "/api/articulos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: "{",
    });
    assert.equal(jsonMalFormado.status, 400);
    assert.equal((await jsonMalFormado.json()).error, "JSON inválido");
  } finally {
    await api.cerrar();
  }
});

test("responde 404 para artículos inexistentes y habilita CORS", async () => {
  const api = await iniciarApi();
  try {
    const noEncontrado = await fetch(api.baseUrl + "/api/articulos/999");
    assert.equal(noEncontrado.status, 404);
    assert.equal((await noEncontrado.json()).error, "No existe el artículo con id 999");

    const opciones = await fetch(api.baseUrl + "/api/articulos", { method: "OPTIONS" });
    assert.equal(opciones.status, 204);
    assert.equal(opciones.headers.get("access-control-allow-origin"), "*");
  } finally {
    await api.cerrar();
  }
});

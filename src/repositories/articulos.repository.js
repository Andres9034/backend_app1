const fs = require("node:fs");
const path = require("node:path");

const ARTICULOS_INICIALES = [
  { id: 1, nombre: "Bolígrafo azul", categoria: "Escritura", cantidad: 40, precio: 1500 },
  { id: 2, nombre: "Resma de papel carta", categoria: "Papelería", cantidad: 12, precio: 18500 },
  { id: 3, nombre: "Grapadora metálica", categoria: "Organización", cantidad: 8, precio: 22000 },
  { id: 4, nombre: "Carpeta oficio", categoria: "Organización", cantidad: 25, precio: 3500 },
  { id: 5, nombre: "Caja de clips", categoria: "Organización", cantidad: 30, precio: 2500 },
  { id: 6, nombre: "Tijeras de oficina", categoria: "Herramientas", cantidad: 10, precio: 8900 },
];

class ArticulosRepository {
  constructor(rutaArchivo) {
    this.rutaArchivo = rutaArchivo;
    this.datos = this.cargar();
  }

  cargar() {
    if (!fs.existsSync(this.rutaArchivo)) {
      const datosIniciales = {
        siguienteId: ARTICULOS_INICIALES.length + 1,
        articulos: ARTICULOS_INICIALES,
      };
      this.guardarDatos(datosIniciales);
      return datosIniciales;
    }

    const datos = JSON.parse(fs.readFileSync(this.rutaArchivo, "utf8"));
    if (
      !datos ||
      !Array.isArray(datos.articulos) ||
      !Number.isSafeInteger(datos.siguienteId) ||
      datos.siguienteId < 1
    ) {
      throw new Error("El archivo de datos no tiene un formato válido: " + this.rutaArchivo);
    }
    return datos;
  }

  listar() {
    return this.datos.articulos;
  }

  obtenerPorId(id) {
    return this.datos.articulos.find((articulo) => articulo.id === id);
  }

  crear(campos) {
    const articulo = { id: this.datos.siguienteId++, ...campos };
    this.datos.articulos.push(articulo);
    this.guardar();
    return articulo;
  }

  actualizar(id, campos) {
    const indice = this.datos.articulos.findIndex((articulo) => articulo.id === id);
    const articulo = { id, ...campos };
    this.datos.articulos[indice] = articulo;
    this.guardar();
    return articulo;
  }

  eliminar(id) {
    const indice = this.datos.articulos.findIndex((articulo) => articulo.id === id);
    this.datos.articulos.splice(indice, 1);
    this.guardar();
  }

  guardar() {
    this.guardarDatos(this.datos);
  }

  guardarDatos(datos) {
    fs.mkdirSync(path.dirname(this.rutaArchivo), { recursive: true });
    const archivoTemporal = this.rutaArchivo + "." + process.pid + ".tmp";
    fs.writeFileSync(archivoTemporal, JSON.stringify(datos, null, 2) + "\n", "utf8");
    fs.renameSync(archivoTemporal, this.rutaArchivo);
  }
}

module.exports = ArticulosRepository;

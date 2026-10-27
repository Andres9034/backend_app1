const crearApp = require("./app");

const PORT = Number(process.env.PORT || 8080);
const app = crearApp();

app.listen(PORT, () => {
  console.log("API de artículos disponible en http://localhost:" + PORT);
});

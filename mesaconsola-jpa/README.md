# API REST de mesas con JPA

Proyecto Spring Boot con el mismo CRUD REST de `mesaconsola-jdbc`, implementado con JPA/Hibernate siguiendo la estructura de `productoconsola`.

## Requisitos

- Java 21 y Maven
- MySQL con una base de datos accesible

Configura `DB_URL`, `DB_USER` y `DB_PASSWORD`. Los valores por defecto apuntan a `jdbc:mysql://localhost:3306/polirestaurante`, usuario `root` y contraseña vacía. Hibernate actualiza la tabla `mesa` al iniciar.

Desde esta carpeta, ejecuta:

```sh
mvn spring-boot:run
```

## Endpoints

Base URL: `http://localhost:8080/api/mesas`

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/mesas` | 200, lista de mesas |
| GET | `/api/mesas/{id}` | 200 o 404 |
| POST | `/api/mesas` | 201 al crear |
| PUT | `/api/mesas/{id}` | 200 al actualizar o 404 |
| DELETE | `/api/mesas/{id}` | 204 al eliminar o 404 |

Ejemplo para POST y PUT:

```json
{
  "numero": 12,
  "estado": "LIBRE",
  "pedidoId": null
}
```

## Prueba en Postman

Importa `postman/Mesas.postman_collection.json` y ejecuta la colección en orden. Prueba crear, listar, consultar, actualizar, eliminar y comprobar el 404 posterior; cada solicitud incluye verificaciones de estado y respuesta. La colección usa `http://localhost:8080` como URL base.
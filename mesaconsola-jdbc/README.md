# API REST de mesas — Spring Boot + JDBC + MySQL

El proyecto conserva el CRUD JDBC existente y agrega una API REST para probar las operaciones desde Postman.

## Requisitos
- Java 21
- Maven 3.9+
- MySQL accesible y tabla `mesa` creada (ver `sql/mesa.sql`)
- Variables de entorno de conexión: `DB_URL`, `DB_USER`, `DB_PASSWORD`

Por seguridad, las credenciales no están guardadas en el código. Por defecto usa `jdbc:mysql://localhost:3306/polirestaurante`, usuario `root` y contraseña vacía.

## Configurar y ejecutar (PowerShell)
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/polirestaurante"
$env:DB_USER="root"
$env:DB_PASSWORD="TU_CLAVE"
mvn spring-boot:run
```
En CMD puedes usar `set DB_URL=...`, `set DB_USER=...` y `set DB_PASSWORD=...` antes de `mvn spring-boot:run`.

La API queda en `http://localhost:8080`.

## Endpoints para Postman

Base URL: `http://localhost:8080/api/mesas`

| Método | URL | Función |
|---|---|---|
| GET | `/api/mesas` | Listar mesas |
| GET | `/api/mesas/1` | Consultar mesa por ID |
| POST | `/api/mesas` | Crear mesa |
| PUT | `/api/mesas/1` | Reemplazar los datos de una mesa |
| DELETE | `/api/mesas/1` | Eliminar mesa |
| PATCH | `/api/mesas/1/ocupar` | Marcar como ocupada |
| PATCH | `/api/mesas/1/liberar` | Marcar como libre y quitar pedido |

Para POST y PUT, selecciona **Body → raw → JSON** y usa:
```json
{
  "numero": 12,
  "estado": "LIBRE",
  "pedidoId": null
}
```
`numero` es obligatorio y debe ser mayor que cero. `estado` acepta `LIBRE` u `OCUPADA`; si se omite, queda `LIBRE`. `pedidoId` puede ser un número o `null`.

Los códigos de respuesta habituales son `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request` y `404 Not Found`.

## Nota
La API requiere que la tabla `mesa` exista en la base de datos. Si `pedido_id` tiene una llave foránea, el pedido indicado debe existir. La API sigue usando SQL explícito con `PreparedStatement`; no usa JPA/Hibernate.

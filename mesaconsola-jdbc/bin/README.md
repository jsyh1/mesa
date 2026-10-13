# CRUD Mesa por consola — JDBC

Versión del proyecto `mesaconsola` que **no usa JPA ni Hibernate**. Todas las operaciones de base de datos se hacen con JDBC y SQL explícito (`INSERT`, `SELECT`, `UPDATE` y `DELETE`), usando `PreparedStatement`.

## Tecnologías
- Java 21
- Maven
- JDBC
- MySQL

## Estructura

- `mesaconsola.model.Mesa`: modelo Java simple, sin anotaciones de persistencia.
- `mesaconsola.DAO.MesaDAO`: contrato del CRUD.
- `mesaconsola.DAO.MesaDAOImpl`: SQL JDBC para crear, listar, buscar, actualizar y eliminar mesas.
- `mesaconsola.util.ConexionDB`: abre conexiones JDBC.
- `mesaconsola.model.Main`: menú por consola.

## Configurar conexión

Por defecto intenta conectarse a la base local `polirestaurante` con usuario `root` y contraseña vacía. Puedes configurar las variables de entorno:

- `DB_URL` (por defecto `jdbc:mysql://localhost:3306/polirestaurante`)
- `DB_USER` (por defecto `root`)
- `DB_PASSWORD` (por defecto vacío)

Windows CMD:
```cmd
set DB_URL=jdbc:mysql://localhost:3306/polirestaurante
set DB_USER=root
set DB_PASSWORD=tu_clave
```

PowerShell:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/polirestaurante"
$env:DB_USER="root"
$env:DB_PASSWORD="tu_clave"
```

## Tabla esperada

El proyecto supone que ya existe la tabla `mesa` con las columnas `id`, `numero`, `estado` y `pedido_id`. Si aún no existe, revisa `sql/mesa.sql`. El script no crea la tabla `pedido` ni modifica sus relaciones.

## Ejecutar en Eclipse
1. Importa la carpeta como **Existing Maven Project**.
2. Espera a que Maven descargue MySQL Connector/J.
3. Verifica la conexión y la tabla.
4. Ejecuta `mesaconsola.model.Main`.

## Menú
1. Crear mesa.
2. Listar mesas.
3. Buscar mesa por ID.
4. Actualizar mesa.
5. Eliminar mesa.
6. Ocupar mesa.
7. Liberar mesa.
0. Salir.

Las sentencias SQL están en `MesaDAOImpl`; no se usa EntityManager, entidades JPA ni `persistence.xml`.

package mesaconsola.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centraliza las conexiones JDBC. Configura credenciales mediante variables de entorno. */
public final class ConexionDB {
    private static final String URL = System.getenv().getOrDefault(
        "DB_URL", "jdbc:mysql://avnadmin:AVNS_V-atWthcTF5fFxg3qZB@polirestaurante-db-polirestaurante.j.aivencloud.com:26252/defaultdb?ssl-mode=REQUIRED");
    private static final String USUARIO = System.getenv().getOrDefault("DB_USER", "avnadmin");
    private static final String CLAVE = System.getenv().getOrDefault("DB_PASSWORD", "AVNS_V-atWthcTF5fFxg3qZB");

    private ConexionDB() {}

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}

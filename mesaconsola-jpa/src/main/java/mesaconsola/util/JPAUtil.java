package mesaconsola.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class JPAUtil {
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("mesaPU", propiedadesConexion());

    private JPAUtil() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void cerrar() {
        FACTORY.close();
    }

    private static Map<String, Object> propiedadesConexion() {
        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("jakarta.persistence.jdbc.url", variable("DB_URL", "jdbc:mysql://localhost:3306/polirestaurante"));
        propiedades.put("jakarta.persistence.jdbc.user", variable("DB_USER", "root"));
        propiedades.put("jakarta.persistence.jdbc.password", variable("DB_PASSWORD", ""));
        return propiedades;
    }

    private static String variable(String nombre, String valorPorDefecto) {
        return System.getenv().getOrDefault(nombre, valorPorDefecto);
    }
}
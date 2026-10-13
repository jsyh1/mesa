package productoconsola.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("productoPU");

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void cerrar() {
        FACTORY.close();
    }
}
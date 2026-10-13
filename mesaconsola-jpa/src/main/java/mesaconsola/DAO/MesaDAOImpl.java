package mesaconsola.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mesaconsola.model.Mesa;
import mesaconsola.util.JPAUtil;

import java.util.List;

public class MesaDAOImpl implements MesaDAO {

    @Override
    public Mesa crear(Mesa mesa) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(mesa);
            tx.commit();
            return mesa;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mesa> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Mesa m ORDER BY m.id", Mesa.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Mesa buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Mesa.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Mesa actualizar(Mesa mesa) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Mesa actualizada = em.merge(mesa);
            tx.commit();
            return actualizada;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Mesa mesa = em.find(Mesa.class, id);
            if (mesa == null) {
                tx.rollback();
                return false;
            }
            em.remove(mesa);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
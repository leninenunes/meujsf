/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import facade.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Efetivo;
import model.Funcao;

/**
 *
 * @author lenine
 */
public class EfetivoJpaController implements Serializable {

    public EfetivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Efetivo efetivo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcao funcaoId = efetivo.getFuncaoId();
            if (funcaoId != null) {
                funcaoId = em.getReference(funcaoId.getClass(), funcaoId.getId());
                efetivo.setFuncaoId(funcaoId);
            }
            em.persist(efetivo);
            if (funcaoId != null) {
                funcaoId.getEfetivoCollection().add(efetivo);
                funcaoId = em.merge(funcaoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Efetivo efetivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Efetivo persistentEfetivo = em.find(Efetivo.class, efetivo.getId());
            Funcao funcaoIdOld = persistentEfetivo.getFuncaoId();
            Funcao funcaoIdNew = efetivo.getFuncaoId();
            if (funcaoIdNew != null) {
                funcaoIdNew = em.getReference(funcaoIdNew.getClass(), funcaoIdNew.getId());
                efetivo.setFuncaoId(funcaoIdNew);
            }
            efetivo = em.merge(efetivo);
            if (funcaoIdOld != null && !funcaoIdOld.equals(funcaoIdNew)) {
                funcaoIdOld.getEfetivoCollection().remove(efetivo);
                funcaoIdOld = em.merge(funcaoIdOld);
            }
            if (funcaoIdNew != null && !funcaoIdNew.equals(funcaoIdOld)) {
                funcaoIdNew.getEfetivoCollection().add(efetivo);
                funcaoIdNew = em.merge(funcaoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = efetivo.getId();
                if (findEfetivo(id) == null) {
                    throw new NonexistentEntityException("The efetivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Efetivo efetivo;
            try {
                efetivo = em.getReference(Efetivo.class, id);
                efetivo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The efetivo with id " + id + " no longer exists.", enfe);
            }
            Funcao funcaoId = efetivo.getFuncaoId();
            if (funcaoId != null) {
                funcaoId.getEfetivoCollection().remove(efetivo);
                funcaoId = em.merge(funcaoId);
            }
            em.remove(efetivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Efetivo> findEfetivoEntities() {
        return findEfetivoEntities(true, -1, -1);
    }

    public List<Efetivo> findEfetivoEntities(int maxResults, int firstResult) {
        return findEfetivoEntities(false, maxResults, firstResult);
    }

    private List<Efetivo> findEfetivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Efetivo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Efetivo findEfetivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Efetivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEfetivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Efetivo> rt = cq.from(Efetivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import facade.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import model.RdcEfetivo;
import model.RdcEfetivo_;

/**
 *
 * @author lenine.nunes
 */
public class RdcEfetivoJpaController implements Serializable {

    public RdcEfetivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RdcEfetivo rdcEfetivo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rdcEfetivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RdcEfetivo rdcEfetivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rdcEfetivo = em.merge(rdcEfetivo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rdcEfetivo.getId();
                if (findRdcEfetivo(id) == null) {
                    throw new NonexistentEntityException("The rdcEfetivo with id " + id + " no longer exists.");
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
            RdcEfetivo rdcEfetivo;
            try {
                rdcEfetivo = em.getReference(RdcEfetivo.class, id);
                rdcEfetivo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rdcEfetivo with id " + id + " no longer exists.", enfe);
            }
            em.remove(rdcEfetivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RdcEfetivo> findRdcEfetivoEntities() {
        return findRdcEfetivoEntities(true, -1, -1);
    }

    public List<RdcEfetivo> findRdcEfetivoEntities(int maxResults, int firstResult) {
        return findRdcEfetivoEntities(false, maxResults, firstResult);
    }

    private List<RdcEfetivo> findRdcEfetivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RdcEfetivo.class));
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
    
    public List<RdcEfetivo> findRdcEfetivoFilter(RdcEfetivo rdcEfetivo, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            Root<RdcEfetivo> rt = cq.from(RdcEfetivo.class);
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(rdcEfetivo.getId() != null && rdcEfetivo.getId() != 0){
                predicates.add(cb.equal(rt.get(RdcEfetivo_.id), rdcEfetivo.getId()));
            }
            if(rdcEfetivo.getCodigo() != null && !rdcEfetivo.getCodigo().equals("")){
                predicates.add(cb.like(rt.get(RdcEfetivo_.codigo), "%" + rdcEfetivo.getCodigo() + "%"));
            }
            cq.where(predicates.toArray(new Predicate[] {}));
            Query q = em.createQuery(cq);
            
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
            
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public RdcEfetivo findRdcEfetivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RdcEfetivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRdcEfetivoCount(RdcEfetivo rdcEfetivo) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RdcEfetivo> rt = cq.from(RdcEfetivo.class);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            List<Predicate> predicates = new ArrayList<Predicate>();
            if(rdcEfetivo.getId() != null && rdcEfetivo.getId() != 0){
                predicates.add(cb.equal(rt.get(RdcEfetivo_.id), rdcEfetivo.getId()));
            }
            if(rdcEfetivo.getCodigo() != null && !rdcEfetivo.getCodigo().equals("")){
                predicates.add(cb.like(rt.get(RdcEfetivo_.codigo), "%" + rdcEfetivo.getCodigo() + "%"));
            }
            cq.where(predicates.toArray(new Predicate[] {}));
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

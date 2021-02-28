/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import facade.exceptions.IllegalOrphanException;
import facade.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Efetivo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Funcao;

/**
 *
 * @author lenine
 */
public class FuncaoJpaController implements Serializable {

    public FuncaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcao funcao) {
        if (funcao.getEfetivoCollection() == null) {
            funcao.setEfetivoCollection(new ArrayList<Efetivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Efetivo> attachedEfetivoCollection = new ArrayList<Efetivo>();
            for (Efetivo efetivoCollectionEfetivoToAttach : funcao.getEfetivoCollection()) {
                efetivoCollectionEfetivoToAttach = em.getReference(efetivoCollectionEfetivoToAttach.getClass(), efetivoCollectionEfetivoToAttach.getId());
                attachedEfetivoCollection.add(efetivoCollectionEfetivoToAttach);
            }
            funcao.setEfetivoCollection(attachedEfetivoCollection);
            em.persist(funcao);
            for (Efetivo efetivoCollectionEfetivo : funcao.getEfetivoCollection()) {
                Funcao oldFuncaoIdOfEfetivoCollectionEfetivo = efetivoCollectionEfetivo.getFuncaoId();
                efetivoCollectionEfetivo.setFuncaoId(funcao);
                efetivoCollectionEfetivo = em.merge(efetivoCollectionEfetivo);
                if (oldFuncaoIdOfEfetivoCollectionEfetivo != null) {
                    oldFuncaoIdOfEfetivoCollectionEfetivo.getEfetivoCollection().remove(efetivoCollectionEfetivo);
                    oldFuncaoIdOfEfetivoCollectionEfetivo = em.merge(oldFuncaoIdOfEfetivoCollectionEfetivo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcao funcao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcao persistentFuncao = em.find(Funcao.class, funcao.getId());
            Collection<Efetivo> efetivoCollectionOld = persistentFuncao.getEfetivoCollection();
            Collection<Efetivo> efetivoCollectionNew = funcao.getEfetivoCollection();
            List<String> illegalOrphanMessages = null;
            for (Efetivo efetivoCollectionOldEfetivo : efetivoCollectionOld) {
                if (!efetivoCollectionNew.contains(efetivoCollectionOldEfetivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Efetivo " + efetivoCollectionOldEfetivo + " since its funcaoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Efetivo> attachedEfetivoCollectionNew = new ArrayList<Efetivo>();
            for (Efetivo efetivoCollectionNewEfetivoToAttach : efetivoCollectionNew) {
                efetivoCollectionNewEfetivoToAttach = em.getReference(efetivoCollectionNewEfetivoToAttach.getClass(), efetivoCollectionNewEfetivoToAttach.getId());
                attachedEfetivoCollectionNew.add(efetivoCollectionNewEfetivoToAttach);
            }
            efetivoCollectionNew = attachedEfetivoCollectionNew;
            funcao.setEfetivoCollection(efetivoCollectionNew);
            funcao = em.merge(funcao);
            for (Efetivo efetivoCollectionNewEfetivo : efetivoCollectionNew) {
                if (!efetivoCollectionOld.contains(efetivoCollectionNewEfetivo)) {
                    Funcao oldFuncaoIdOfEfetivoCollectionNewEfetivo = efetivoCollectionNewEfetivo.getFuncaoId();
                    efetivoCollectionNewEfetivo.setFuncaoId(funcao);
                    efetivoCollectionNewEfetivo = em.merge(efetivoCollectionNewEfetivo);
                    if (oldFuncaoIdOfEfetivoCollectionNewEfetivo != null && !oldFuncaoIdOfEfetivoCollectionNewEfetivo.equals(funcao)) {
                        oldFuncaoIdOfEfetivoCollectionNewEfetivo.getEfetivoCollection().remove(efetivoCollectionNewEfetivo);
                        oldFuncaoIdOfEfetivoCollectionNewEfetivo = em.merge(oldFuncaoIdOfEfetivoCollectionNewEfetivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcao.getId();
                if (findFuncao(id) == null) {
                    throw new NonexistentEntityException("The funcao with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcao funcao;
            try {
                funcao = em.getReference(Funcao.class, id);
                funcao.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Efetivo> efetivoCollectionOrphanCheck = funcao.getEfetivoCollection();
            for (Efetivo efetivoCollectionOrphanCheckEfetivo : efetivoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcao (" + funcao + ") cannot be destroyed since the Efetivo " + efetivoCollectionOrphanCheckEfetivo + " in its efetivoCollection field has a non-nullable funcaoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(funcao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcao> findFuncaoEntities() {
        return findFuncaoEntities(true, -1, -1);
    }

    public List<Funcao> findFuncaoEntities(int maxResults, int firstResult) {
        return findFuncaoEntities(false, maxResults, firstResult);
    }

    private List<Funcao> findFuncaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcao.class));
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

    public Funcao findFuncao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcao.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcao> rt = cq.from(Funcao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

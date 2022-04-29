package hibernateControllers;

import bookds.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserHibControl {
    private EntityManagerFactory emf = null;

    public UserHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createUser(User user) {
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user); //INSERT
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(em != null) em.close();
        }
    }
    public void updateUser(User user) {
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(user); //update
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(em != null) em.close();
        }
    }
    public void removeUser(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = null;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (Exception e) {
                System.out.println("No such user by given Id");
            }
            for (Cart c : user.getMyOrders()) {
                c.getSupervisingEmployees().remove(user);
                em.merge(c);
            }
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> getAllUsers() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    public List getAllIndividual() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Individual.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    public Individual getIndividualById(int id) {
        EntityManager em = null;
        Individual individual = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            individual = em.find(Individual.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return individual;
    }
    public LegalPerson getLegalPersonById(int id) {
        EntityManager em = null;
        LegalPerson legalPerson = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            legalPerson = em.find(LegalPerson.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return legalPerson;
    }
    public List getAllLegalPersons() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(LegalPerson.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    public User getUserById(int id) {
        EntityManager em = null;
        User user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;
    }

    public User getUserByLoginData(String login, String password) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.like(root.get("login"), login));
        query.select(root).where(cb.like(root.get("password"), password));
        Query q;
        try {
            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}

package hibernateControllers;

import bookds.*;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;

public class CartHibControl {
    private EntityManagerFactory emf = null;
    public CartHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createCart(Cart cart) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cart);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void updateOrder(Cart cart) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(cart);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(em != null) {
                em.close();
            }
        }
    }
   public void removeOrder(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cart cart = null;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getId();
            } catch (Exception e) {
                System.out.println("No such book by given ID");
            }

            for(Book b : cart.getItems()) {
                removeOrder(b.getId());
            }
            cart.getItems().clear();
            em.merge(cart);

            for (User u : cart.getSupervisingEmployees()) {
                u.getMyOrders().remove(cart);
                em.merge(u);
            }

            em.remove(cart);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Cart getOrderById(int id) {
        EntityManager em;
        Cart cart = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cart = em.find(Cart.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such order by given Id");
        }
        return cart;
    }
    public List<Cart> getAllOrders() { return getAllOrder(); }

    public List<Cart> getAllOrder() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Cart.class));
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

    public List<Cart> getOrderByUserId(int id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cart> query = cb.createQuery(Cart.class);

        Root<Cart> root = query.from(Cart.class);

        CriteriaBuilder.In<Integer> inClause = cb.in(root.get("id"));
        Individual individual = em.getReference(Individual.class, id);
        for (Cart c : individual.getMyManagedOrders()) {
            inClause.value(c.getId());
        }
        query.select(root).where(inClause);
        Query q;
        try {
            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Cart> getMyOrders(int id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cart> query = cb.createQuery(Cart.class);

        Root<Cart> root = query.from(Cart.class);

        CriteriaBuilder.In<Integer> inClause = cb.in(root.get("id"));
        User user = em.getReference(User.class, id);
        for (Cart c : user.getMyOrders()) {
            inClause.value(c.getId());
        }
        query.select(root).where(inClause);
        Query q;
        try {
            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List getFilteredOrders(Status status, Double startPrice, Double endPrice, LocalDate orderDateStart, LocalDate orderDateEnd) {
        if(orderDateStart == null) orderDateStart = LocalDate.parse("1500-01-01");
        if(orderDateEnd == null) orderDateEnd = LocalDate.parse("2200-01-01");
        if(startPrice == null) startPrice = MIN_VALUE;
        if(endPrice == null) endPrice = MAX_VALUE;
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);

            query.select(root).where(cb.and(cb.equal(root.get("status"), status)), cb.between(root.get("totalPrice"), startPrice, endPrice), cb.between(root.get("dateCreated"), orderDateStart, orderDateEnd));

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

}

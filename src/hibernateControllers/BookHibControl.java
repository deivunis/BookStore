package hibernateControllers;

import bookds.Book;
import bookds.Cart;
import bookds.Comment;
import bookds.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

public class BookHibControl {

    private EntityManagerFactory emf = null;

    public BookHibControl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createBook(Book book) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void updateBook(Book book) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(book); //UPDATE
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
    public void editBook(Book book) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(book);
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
    public void removeBook(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book book = null;
            try {
                book = em.getReference(Book.class, id);
                book.getId();
            } catch (Exception e) {
                System.out.println("No such book by given ID");
            }
            em.remove(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Book> getAllBooks() { return getAllBook(); }

    public List<Book> getAllBook() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Book.class));
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
    public Book getBookById(int id) {
        EntityManager em;
        Book book = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            book = em.find(Book.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such book by given Id");
        }
        return book;
    }
    public Comment getCommentById(int id) {
        EntityManager em;
        Comment comment = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comment = em.find(Comment.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such comment by given Id");
        }
        return comment;
    }
    public void removeComment(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comment comment = null;
            try {
                comment = em.getReference(Comment.class, id);
                comment.getId();
            } catch (Exception e) {
                System.out.println("No such comment by given ID");
            }
            Book book = comment.getBookComment();
            if(book != null) {
                book.getComments().remove(comment);
                em.merge(book);
            }
            for(Comment c : comment.getReplies()) {
                removeComment(c.getId());
            }

            comment.getReplies().clear();
            em.merge(comment);

            em.remove(comment);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void updateComment(Comment comment) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(comment);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Book> getBookBySearch(String title, String authors) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            query.select(root).where(criteriaBuilder.or((criteriaBuilder.like(root.get("bookTitle"), title)), (criteriaBuilder.like(root.get("authors"), authors))));
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
    /*public List<Book> getBookByUserId(int id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);

        Root<Book> root = query.from(Book.class);

        CriteriaBuilder.In<Integer> inClause = cb.in(root.get("id"));
        User user = em.getReference(User.class, id);
        for (Book p : user.) {
            inClause.value(p.getId());
        }
        query.select(root).where(inClause);
        Query q;
        try {
            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }*/
}

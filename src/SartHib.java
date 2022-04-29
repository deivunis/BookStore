
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SartHib {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    }
}

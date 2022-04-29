package NotInUse;
/*
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter

public class BookShop implements Serializable, Shop {

    private ArrayList<Book> allBooks;
    private ArrayList<User> allUsers;
    private ArrayList<Cart> allOrders;

    public BookShop() {
        this.allBooks = new ArrayList<>();
        this.allUsers = new ArrayList<>();
        this.allOrders= new ArrayList<>();
    }


    @Override
    public void contactSupport() {

    }

    @Override
    public int employeeInfo() {
        return 0;
    }

    @Override
    public String sendComplaint(String messageText) {
        return null;
    }













    public User getUserByLogin(String login) {
        return allUsers.stream().filter(u->u.getLogin().equals(login)).findFirst().orElse(null);
    }

    public void deleteUserByLogin(String login) {
        User user = getUserByLogin(login);
        if(user != null) {
            getAllUsers().remove(user);
        }
        else System.out.println("No user with given login.");
    }


    public void printUserData(String login){
        User user = getUserByLogin(login);
        if(user != null) {
            System.out.println(user);
        }
        else System.out.println("No user with given login.");
    }
}
*/
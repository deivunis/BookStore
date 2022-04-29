package bookds;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Data
@Entity

public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    private String address;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    @Enumerated
    private Role role;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cart> myOrders;

    //Norint neleisti hibernate kurti lenteliu
    /*@Transient
    private String unsavedField;
    @Transient
    private String unsavedField2;
     */

    public User(String login, String password, String email, String address, LocalDate dateCreated, LocalDate dateModified) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;

    }

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
        this.role = Role.CUSTOMER;
    }

    public User(String login, String password, String email, String address, Role role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

}


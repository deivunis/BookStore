package bookds;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Data
@Entity

public final class Individual extends User implements Serializable {
    //login, password, dateCreated, dateModified yra aprasyti User klaseje, ju nereikia is naujo aprasyti;
    private String name;
    private String surname;
    private String phoneNumber;
    private LocalDate birthDate;
    @ManyToMany(mappedBy = "supervisingEmployees", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cart> myManagedOrders;

    public Individual(String login, String password, String email, String address, Role role, String name, String surname, String phoneNumber, LocalDate birthDate) {
        super(login, password, email, address, role);
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

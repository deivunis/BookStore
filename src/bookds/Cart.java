package bookds;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
//@Data
@Entity

public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    private LocalDate modifiedDate;
    private String customerName;
    private String shippingAddress;
    private Double cartPrice;
    private Double cartSalePrice;
    private String customerEmail;
    private String companyTitle;
    private String companyRegNum;
    private String companyVATNum;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> items;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Individual> supervisingEmployees;
    @ManyToOne
    private User buyer;
    private double totalPrice;
    @Enumerated
    private Status status;

    public Cart(LocalDate dateCreated, LocalDate modifiedDate, String customerName, String shippingAddress,String customerEmail, List<Book> itemsPrice, List<Book> itemsSalePrice, List<Book> itemsTotalPrice,List<Book> items, List<Individual> supervisingEmployees, Status status, String companyTitle, String companyRegNum, String companyVATNum, User buyer) {
        this.dateCreated = dateCreated;
        this.modifiedDate = modifiedDate;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.customerEmail = customerEmail;
        this.cartPrice = itemsPrice.stream()
                .mapToDouble(a -> a.getPrice())
                .sum(); ;
        this.cartSalePrice = itemsSalePrice.stream()
                .mapToDouble(a -> a.getSalePrice())
                .sum();;
        this.items = items;
        this.supervisingEmployees = supervisingEmployees;
        this.totalPrice = itemsTotalPrice.stream()
                .mapToDouble(a -> a.getSalePrice())
                .sum();
        this.status = status;
        this.companyTitle = companyTitle;
        this.companyRegNum = companyRegNum;
        this.companyVATNum = companyVATNum;
        this.buyer = buyer;
    }

    public Cart(LocalDate dateCreated, LocalDate modifiedDate, String customerName, String shippingAddress, Double cartPrice, Double cartSalePrice, String customerEmail, double totalPrice, Status status) {
        this.dateCreated = dateCreated;
        this.modifiedDate = modifiedDate;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.cartPrice = cartPrice;
        this.cartSalePrice = cartSalePrice;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.status = status;
    }

}

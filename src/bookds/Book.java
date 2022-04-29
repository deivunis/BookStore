package bookds;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Data
@Entity

public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bookTitle;
    private String description;
    private LocalDate publishDate;
    private Double price;
    private Double salePrice;
    private String stockQuantity;
    private String authors;
    private int pageNum;
    private int edition;
    @OneToMany(mappedBy = "bookComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments;
    @ManyToMany(mappedBy = "items", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Cart> inOrders;
    @Enumerated
    private Genre genre;

    //Enum category; (TopRated, BestSelling, Featured, OnSale)


    public Book(int id, String bookTitle, String description, LocalDate publishDate, Double price, Double salePrice, String stockQuantity, String authors, int pageNum, int edition, List<Comment> comments, Genre genre) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.description = description;
        this.publishDate = publishDate;
        this.price = price;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
        this.authors = authors;
        this.pageNum = pageNum;
        this.edition = edition;
        this.comments = comments;
        this.genre = genre;
    }

    public Book(String bookTitle, LocalDate publishDate, int pageNum, String authors, int edition, String description, Genre genre, double price, double salePrice, String stockQuantity) {
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.pageNum = pageNum;
        this.authors = authors;
        this.edition = edition;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.salePrice = salePrice;
        this.stockQuantity = stockQuantity;
    }

    public Book(int anInt, String string, String string1, String string2, int anInt1, double aDouble, Date date, double aDouble1, String string3, int anInt2, Genre valueOf, int anInt3) {
    }

    public Book(String text) {
    }
}

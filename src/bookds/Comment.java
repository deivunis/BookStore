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

public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commenterName;
    private LocalDate dateCreated;
    private Double rating;
    private String commentText;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Book bookComment;

    public Comment(String commenterName, String commentTitle, String commentText, Comment parentComment, Book bookComment, LocalDate dateCreated) {
        this.commenterName = commenterName;
        this.commentTitle = commentTitle;
        this.commentText = commentText;
        this.parentComment = parentComment;
        this.bookComment = bookComment;
        this.dateCreated = dateCreated;
    }

    public Comment(String commenterName, String commentTitle, String commentText, LocalDate dateCreated, Double rating) {
        this.commenterName = commenterName;
        this.commentTitle = commentTitle;
        this.commentText = commentText;
        this.dateCreated = dateCreated;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Date: " + dateCreated + " Author: " + commenterName + "\nTitle: " + commentTitle +
                "\nText: " + commentText;
    }

}

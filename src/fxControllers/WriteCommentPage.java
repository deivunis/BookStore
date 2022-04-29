package fxControllers;

import bookds.Book;
import bookds.Comment;
import bookds.Individual;
import bookds.User;
import hibernateControllers.BookHibControl;
import hibernateControllers.CartHibControl;
import hibernateControllers.UserHibControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;

public class WriteCommentPage {
    public TextArea commentText;
    public TextField commentTitle;
    public Button cancel;
    private int bookId;
    private int parentCommentId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);
    BookHibControl bookHibControl = new BookHibControl(entityManagerFactory);
    CartHibControl cartHibControl = new CartHibControl(entityManagerFactory);
    private int userId;

    public void setUserData(int id) {
        this.userId = id;
    }
    public void setBookId(int bookId, int parentCommentId) {
        this.bookId = bookId;
        this.parentCommentId = parentCommentId;
    }

    public void saveComment(ActionEvent event) throws IOException {
        if (bookId != 0) {
            Book book = bookHibControl.getBookById(bookId);
            Comment comment = new Comment(((Individual) userHibControl.getUserById(userId)).getName(), commentTitle.getText(), commentText.getText(), null, book,
                    LocalDate.now());
            book.getComments().add(comment);
            bookHibControl.updateBook(book);
            returnFromComment();
        }
        else if(parentCommentId != 0) {
            Comment parentComment = bookHibControl.getCommentById(parentCommentId);
            Comment comment = new Comment(((Individual) userHibControl.getUserById(userId)).getName(), commentTitle.getText(), commentText.getText(), parentComment, null,
                    LocalDate.now());
            parentComment.getReplies().add(comment);
            bookHibControl.updateComment(parentComment);
            returnFromComment();
        }
    }

    public void returnFromComment() throws IOException {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

}

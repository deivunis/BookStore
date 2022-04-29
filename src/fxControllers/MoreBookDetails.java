package fxControllers;

import bookds.Book;
import bookds.Genre;
import hibernateControllers.BookHibControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class MoreBookDetails {
    @FXML
    public TextField bookTitle;
    @FXML
    public ComboBox genre;
    @FXML
    public TextArea bookDesc;
    @FXML
    public DatePicker pubDate;
    @FXML
    public TextField pgNum;
    @FXML
    public TextField edition;
    @FXML
    public TextField priceF;
    @FXML
    public TextField salePriceF;
    @FXML
    public TextArea authors;
    @FXML
    public TextField quantityF;

    private int currentBookId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    BookHibControl bookHibControl = new BookHibControl(entityManagerFactory);

    public void setData(int currentBookId) {
        this.currentBookId = currentBookId;
            loadBookData();
    }

    private void loadBookData() {
        Book book = bookHibControl.getBookById(currentBookId);
        bookTitle.setText(book.getBookTitle());
        genre.setValue(book.getGenre());
        //genre.setSelectionModel(boo);
        bookDesc.setText(book.getDescription());
        pubDate.setValue(book.getPublishDate());
        pgNum.setText(String.valueOf(book.getPageNum()));
        edition.setText(String.valueOf(book.getEdition()));
        priceF.setText(String.valueOf(book.getPrice()));
        salePriceF.setText(String.valueOf(book.getSalePrice()));
        quantityF.setText(book.getStockQuantity());
        authors.setText(book.getAuthors());
    }

    public void returnToShop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MoreBookDetails.class.getResource("../view/main-book-shop-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) bookTitle.getScene().getWindow();
        stage.setTitle("Book store");
        stage.setScene(scene);
        stage.show();
    }
}

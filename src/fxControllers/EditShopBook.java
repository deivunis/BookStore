package fxControllers;

import bookds.Book;
import bookds.Genre;
import bookds.User;
import hibernateControllers.BookHibControl;
import hibernateControllers.UserHibControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static utils.WarningMessages.alertMessage;

public class EditShopBook implements Initializable {
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

    private int userId;
    private int currentBookId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    BookHibControl bookHibControl = new BookHibControl(entityManagerFactory);
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);

    public void setData(boolean edit, int currentBookId) {
        this.currentBookId = currentBookId;
        if(edit) {
            loadBookData();
        }
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


    public void saveBook(ActionEvent actionEvent) throws IOException {
        if(bookTitle.getText().isEmpty() || bookDesc.getText().isEmpty() || genre.getSelectionModel().isEmpty() || pubDate.getValue().toString().isEmpty() ||
        edition.getText().isEmpty() || priceF.getText().isEmpty() || salePriceF.getText().isEmpty() || quantityF.getText().isEmpty() || authors.getText().isEmpty() ||
        pgNum.getText().isEmpty()) {
            alertMessage("All fields must be filled and cannot be left empty!");
        } else if(!Pattern.matches("[0-9]", edition.getText()) || !Pattern.matches("[0-9]", priceF.getText()) ||
                !Pattern.matches("[0-9]", salePriceF.getText()) || !Pattern.matches("[0-9]", pgNum.getText())) {
            alertMessage("Edition, price, sale price or page number fields must have only digits!");
        } else if(!Pattern.matches("[a-zA-Z]", authors.getText())) {
            alertMessage("Authors field cannot have symbols or digits!");
        } else {
            Book book = bookHibControl.getBookById(currentBookId);
            book.setBookTitle(bookTitle.getText());
            book.setDescription(bookDesc.getText());
            book.setGenre(Genre.valueOf(genre.getSelectionModel().getSelectedItem().toString()));
            book.setPublishDate(pubDate.getValue());
            book.setEdition(Integer.parseInt(edition.getText()));
            book.setPrice(Double.parseDouble(priceF.getText()));
            book.setSalePrice(Double.parseDouble(salePriceF.getText()));
            book.setStockQuantity(quantityF.getText());
            book.setAuthors(authors.getText());
            book.setPageNum(Integer.parseInt(pgNum.getText()));

            bookHibControl.editBook(book);

            returnToMainMenu();
        }

    }

    public void returnToMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditShopBook.class.getResource("../view/main-book-shop-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) bookTitle.getScene().getWindow();
        stage.setTitle("Book store");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //genre.getItems().clear();
        genre.getItems().addAll("SCI_FI", "FANTASY", "MYSTERY", "ROMANCE", "DRAMA", "ACTION", "THRILLER", "MANGA");
    }
}

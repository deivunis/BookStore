package fxControllers;

import bookds.*;
import hibernateControllers.BookHibControl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import hibernateControllers.CartHibControl;
import hibernateControllers.UserHibControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.DatabaseOperations;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static utils.WarningMessages.alertMessage;

public class MainBookShopPage implements Initializable {
    @FXML
    public ListView productList;
    @FXML
    public TreeView commentsBook;
    @FXML
    public ListView shopBookList;
    @FXML
    public TextField searchTitle;
    @FXML
    public DatePicker searchPublish;
    @FXML
    public TextField searchAuthor;
    @FXML
    public ComboBox searchGenre;
    @FXML
    public TextField bookTitle;
    @FXML
    public TextArea bookDesc;
    @FXML
    public DatePicker pubDate;
    @FXML
    public TextField pgNum;
    @FXML
    public TextField edition;
    @FXML
    public ComboBox genre;
    @FXML
    public TextArea authors;
    @FXML
    public MenuItem editItem;
    @FXML
    public MenuItem viewInfoItem;
    @FXML
    public ListView orderList;
    @FXML
    public TextField priceF;
    @FXML
    public TextField salePriceF;
    @FXML
    public TextField quantityF;
    @FXML
    public ListView orderItemsList;
    @FXML
    public Tab booksTools;
    @FXML
    public Tab orderTools;
    @FXML
    public Tab adminTools;
    @FXML
    public TextArea orderInfoList;
    @FXML
    public Tab bookStore;
    @FXML
    public DatePicker filterByStartDate;
    @FXML
    public DatePicker filterByEndDate;
    @FXML
    public ComboBox filterByStatusF;
    @FXML
    public TextField startPriceF;
    @FXML
    public TextField endPriceF;
    @FXML
    public ListView accOrdersList;
    @FXML
    public Tab myAccount;
    @FXML
    public TextField accLoginF;
    @FXML
    public TextField accEmailF;
    @FXML
    public TextField accNameF;
    @FXML
    public TextField accSurnameF;
    @FXML
    public TextField accAddressF;
    @FXML
    public TextField accCompTitleF;
    @FXML
    public TextField accCeoF;
    @FXML
    public TextField accRegNumF;
    @FXML
    public TextField accVatNumF;
    @FXML
    public TextField accPhoneNumF;
    @FXML
    public DatePicker accBirthdateF;
    @FXML
    public PasswordField accCurrentPassF;
    @FXML
    public PasswordField accNewPassF;
    @FXML
    public PasswordField accConfirmPassF;

    private int userId;
    private int orderId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    BookHibControl bookHibControl = new BookHibControl(entityManagerFactory);
    CartHibControl cartHibControl = new CartHibControl(entityManagerFactory);
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);

    public void setUserData(int id) {
        this.userId = id;
        showTabsByUser();
        fillTables();
    }
    public void setOrderData(int id) {
        this.orderId = orderId;
    }
    private void showTabsByUser() {
        User user = userHibControl.getUserById(userId);
        if(user.getRole() == Role.CUSTOMER) {
            orderTools.setDisable(true);
            booksTools.setDisable(true);
            adminTools.setDisable(true);
            bookStore.setDisable(false);
            myAccount.setDisable(false);
        }
        else if(user.getRole() == Role.EMPLOYEE) {
            adminTools.setDisable(true);
            orderTools.setDisable(false);
            booksTools.setDisable(false);
            bookStore.setDisable(false);
            myAccount.setDisable(false);
        }
        else if(user.getRole() == Role.ADMIN) {
            orderTools.setDisable(false);
            booksTools.setDisable(false);
            adminTools.setDisable(false);
            bookStore.setDisable(false);
            myAccount.setDisable(false);
        }
    }
    private void fillTables() {
        shopBookList.getItems().clear();
        productList.getItems().clear();
        orderList.getItems().clear();
        accOrdersList.getItems().clear();
        User user = userHibControl.getUserById(userId);
        List<Book> myBooksFromDb = bookHibControl.getAllBooks();
        List<Cart> customerOrderListFromDb = cartHibControl.getMyOrders(user.getId());
        for (Book b : myBooksFromDb) {
            shopBookList.getItems().add(b.getId() + ":" + b.getBookTitle() + ":" + b.getStockQuantity() + ":" + b.getPrice() + ":" + b.getSalePrice() + ":" + b.getPublishDate());
        }
        for (Book b : myBooksFromDb) {
            productList.getItems().add(b.getId() + "    '" + b.getBookTitle() + "'    " + b.getStockQuantity() + "qty" + "    " + b.getPrice() + "    " + b.getSalePrice() + "€" + "    " + b.getPublishDate());
        }
        if(user.getRole() == Role.ADMIN) {
            List<Cart> adminOrderListFromDb = cartHibControl.getAllOrder();
            for(Cart c : adminOrderListFromDb) {
                orderList.getItems().add(c.getId() + ":" + c.getDateCreated() + ":" + c.getTotalPrice() + "€:" + c.getStatus());
            }
        } else if(user.getRole() == Role.EMPLOYEE) {
            List<Cart> employeeOrderListFromDb = cartHibControl.getOrderByUserId(user.getId());
            for(Cart c : employeeOrderListFromDb) {
                orderList.getItems().add(c.getId() + ":" + c.getDateCreated() + ":" + c.getTotalPrice() + "€:" + c.getStatus());
            }
        }
        for(Cart c : customerOrderListFromDb) {
            accOrdersList.getItems().add(c.getId() + ":" + c.getDateCreated() + ":" + c.getTotalPrice() + "€:" + c.getStatus());
        }
    }

    //------------------------------------------EMPLOYEE TAB START------------------------------------------//
    public void addBook(javafx.event.ActionEvent actionEvent) throws SQLException {
        Book book = new Book(bookTitle.getText(), pubDate.getValue(), Integer.parseInt(pgNum.getText()), authors.getText(), Integer.parseInt(edition.getText()), bookDesc.getText(),
                Genre.valueOf(genre.getSelectionModel().getSelectedItem().toString()), Double.parseDouble(priceF.getText()),Double.parseDouble(salePriceF.getText()), quantityF.getText());
        bookHibControl.createBook(book);
        refreshTable();
        fillTables();
    }
    public void editBook(ActionEvent actionEvent) throws IOException {
        loadEditShopBook(true, Integer.parseInt(shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0]));
    }

    private void loadEditShopBook(boolean edit, int currentBookId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EditShopBook.class.getResource("../view/edit-shop-book.fxml"));
        Parent root = fxmlLoader.load();
        EditShopBook editShopBook = fxmlLoader.getController();
        editShopBook.setData(edit, currentBookId);
        Scene scene = new Scene(root);
        Stage stage = (Stage) shopBookList.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void deleteBook(ActionEvent actionEvent) {
        bookHibControl.removeBook(Integer.parseInt(shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0]));
        refreshTable();
        fillTables();
    }
    public void verifyOrder(ActionEvent actionEvent) {
        Cart cart = cartHibControl.getOrderById(Integer.parseInt(orderList.getSelectionModel().getSelectedItem().toString().split(":")[0]));
        cart.setStatus(Status.VERIFIED);
        cart.setModifiedDate(LocalDate.now());
        cartHibControl.updateOrder(cart);
        fillTables();
    }

    public void rejectOrder(ActionEvent actionEvent) {
        Cart cart = cartHibControl.getOrderById(Integer.parseInt(orderList.getSelectionModel().getSelectedItem().toString().split(":")[0]));
        cart.setStatus(Status.REJECTED);
        cart.setModifiedDate(LocalDate.now());
        cartHibControl.updateOrder(cart);
        fillTables();
    }

    public void setProcessing(ActionEvent actionEvent) {
        Cart cart = cartHibControl.getOrderById(Integer.parseInt(orderList.getSelectionModel().getSelectedItem().toString().split(":")[0]));
        cart.setStatus(Status.PROCESSING);
        cart.setModifiedDate(LocalDate.now());
        cartHibControl.updateOrder(cart);
        fillTables();
    }

    public void addToOrderInfoList(ActionEvent actionEvent) {
        Cart cart = cartHibControl.getOrderById(Integer.parseInt(orderList.getSelectionModel().getSelectedItem().toString().split(":")[0]));
        orderInfoList.setText("Order details: " + "\n   Order ID: " + cart.getId() + "\n   Date created: " + cart.getDateCreated() + "\n   Last time modified: " +
                cart.getModifiedDate() + "\n   Customer: " + cart.getCustomerName() + "\n   Customer email: " + cart.getCustomerEmail() + "\n   Shipping address: " +
                cart.getShippingAddress() + "\n   Price without discount: " + cart.getCartPrice() + "\n   Total price: " + cart.getTotalPrice() +
                "\n   Order status: " + cart.getStatus());

    }

    public void filterOrders() {
        List<Cart> filteredOrders = cartHibControl.getFilteredOrders(Status.valueOf(filterByStatusF.getSelectionModel().getSelectedItem().toString()),
                Double.valueOf(startPriceF.getText()), Double.valueOf(endPriceF.getText()), filterByStartDate.getValue(), filterByEndDate.getValue());
        orderList.getItems().clear();
        filteredOrders.forEach(cart -> orderList.getItems().add(cart.getId() + ":" + cart.getDateCreated() + ":" + cart.getTotalPrice() + "€:" + cart.getStatus()));
    }

    //------------------------------------------EMPLOYEE TAB END------------------------------------------//


    //------------------------------------------CUSTOMER TAB START------------------------------------------//
    private ArrayList<Book> fetchBooks() {

        ArrayList<Book> books = new ArrayList<>();
        List<String> orderItemsListItems = orderItemsList.getItems();
        for (String s : orderItemsListItems) {
            Book book = bookHibControl.getBookById(Integer.parseInt(s.split("   ")[0]));
            books.add(book);
        }
        return books;
    }
    public void createOrder() {
        ArrayList<Book> books = fetchBooks();
        books.forEach(System.out::println);
        setOrderData(orderId);
        User user = userHibControl.getUserById(userId);
        if(user.getClass().getSimpleName().equals("Individual")) {
            Individual individual = (Individual) userHibControl.getUserById(userId);
            Cart cart = new Cart(LocalDate.now(), LocalDate.now(), ((Individual) userHibControl.getUserById(userId)).getName(),
                    userHibControl.getUserById(userId).getAddress(), userHibControl.getUserById(userId).getEmail(), books, books,
                    books, books, new ArrayList<Individual>(), Status.ON_HOLD, null, null, null,
                    individual);
            cartHibControl.createCart(cart);
            updateOrder("I", cart);
        }
        else if(user.getClass().getSimpleName().equals("LegalPerson")) {
            LegalPerson legalPerson = (LegalPerson) userHibControl.getUserById(userId);
            Cart cart = new Cart(LocalDate.now(), LocalDate.now(), ((LegalPerson) userHibControl.getUserById(userId)).getCeo(),
                    userHibControl.getUserById(userId).getAddress(), userHibControl.getUserById(userId).getEmail(), books, books,
                    books, books, new ArrayList<Individual>(), Status.ON_HOLD, ((LegalPerson) userHibControl.getUserById(userId)).getCompanyTitle(),
                    ((LegalPerson) userHibControl.getUserById(userId)).getRegistrationNumber(), ((LegalPerson) userHibControl.getUserById(userId)).getVatNumber(),
                    legalPerson);
            cartHibControl.createCart(cart);
            updateOrder("LP", cart);
        }
        setOrderData(orderId);
        refreshTable();
        fillTables();
    }
    public void updateOrder(String type, Cart cart) {
        User user = userHibControl.getUserById(userId);

        if(type.equals("I")) {
            cart.getSupervisingEmployees().add(userHibControl.getIndividualById(20));
            user.getMyOrders().add(cart);
            cartHibControl.updateOrder(cart);
            userHibControl.updateUser(user);
        }
        else if(type.equals("LP")) {
            cart.getSupervisingEmployees().add(userHibControl.getIndividualById(19));
            user.getMyOrders().add(cart);
            cartHibControl.updateOrder(cart);
            userHibControl.updateUser(user);
        }

    }
    public void addBookToOrder(ActionEvent actionEvent) {
        orderItemsList.getItems().add(productList.getSelectionModel().getSelectedItem());
    }

    public void clearOrder(ActionEvent actionEvent) {
        orderItemsList.getItems().clear();
    }

    public void removeBookFromOrder(ActionEvent actionEvent) {
        orderItemsList.getItems().remove(orderItemsList.getSelectionModel().getSelectedItem());
    }

    public void moreDetails(ActionEvent actionEvent) throws IOException {
        loadMoreBookDetails(Integer.parseInt(productList.getSelectionModel().getSelectedItem().toString().split("   ")[0]));
    }

    private void loadMoreBookDetails(int currentBookId) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MoreBookDetails.class.getResource("../view/more-book-details.fxml"));
        Parent root = fxmlLoader.load();
        MoreBookDetails moreBookDetails = fxmlLoader.getController();
        moreBookDetails.setData(currentBookId);
        Scene scene = new Scene(root);
        Stage stage = (Stage) productList.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void search(ActionEvent actionEvent) {
        productList.getItems().clear();
        List<Book> filteredBooks = bookHibControl.getBookBySearch(searchTitle.getText(), searchAuthor.getText());

        for (Book b : filteredBooks) {
            productList.getItems().add(b.getId() + "    '" + b.getBookTitle() + "'    " + b.getStockQuantity() + "qty" + "    " + b.getPrice() + "€" + "    " + b.getSalePrice() + "€" + "    " + b.getPublishDate());
        }
    }
    private void addTreeItem(Comment comment, TreeItem parent) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parent.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }
    public void loadComments() {
        Book book = bookHibControl.getBookById(Integer.parseInt(productList.getSelectionModel().getSelectedItem().toString().split("    ")[0]));
        commentsBook.setRoot(new TreeItem<String>("Comments:"));
        commentsBook.setShowRoot(false);
        commentsBook.getRoot().setExpanded(true);
        book.getComments().forEach(comment -> addTreeItem(comment, commentsBook.getRoot()));
    }

    public void commentBook() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/write-comment-page.fxml"));
        Parent parent = fxmlLoader.load();
        WriteCommentPage writeCommentPage = fxmlLoader.getController();

        TreeItem<Comment> commentTreeItem = (TreeItem<Comment>) commentsBook.getSelectionModel().getSelectedItem();
        if(commentTreeItem == null) {
            writeCommentPage.setBookId(Integer.parseInt(productList.getSelectionModel().getSelectedItem().toString().split("    ")[0]), 0);
        } else {
            writeCommentPage.setBookId(0, commentTreeItem.getValue().getId());
        }

        writeCommentPage.setUserData(userId);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Book Store");
        stage.setScene(scene);
        stage.showAndWait();
    }
    //------------------------------------------CUSTOMER TAB END------------------------------------------//

    //------------------------------------------ADMIN TAB START-------------------------------------------//
    public void manageUsers(ActionEvent actionEvent) throws IOException {
        loadManageUsers(actionEvent);
    }
    public void loadManageUsers(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserManagement.class.getResource("../view/user-management.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    //------------------------------------------ADMIN TAB END-------------------------------------------//

    //------------------------------------------MY ACCOUNT TAB START-------------------------------------------//
    public void disableAccFields() {
        User user = userHibControl.getUserById(userId);

        if(user.getClass().getSimpleName().equals("Individual")) {
            accCompTitleF.setDisable(true);
            accCeoF.setDisable(true);
            accRegNumF.setDisable(true);
            accVatNumF.setDisable(true);
            //individual fields
            accNameF.setDisable(false);
            accSurnameF.setDisable(false);
            accPhoneNumF.setDisable(false);
            accBirthdateF.setDisable(false);

        } else {
            accNameF.setDisable(true);
            accSurnameF.setDisable(true);
            accPhoneNumF.setDisable(true);
            accBirthdateF.setDisable(true);
            //company fields
            accCompTitleF.setDisable(false);
            accCeoF.setDisable(false);
            accRegNumF.setDisable(false);
            accVatNumF.setDisable(false);
        }
    }
    public void updateAccInfoFields(Event event) {

        User user = userHibControl.getUserById(userId);
        disableAccFields();

        if(user.getClass().getSimpleName().equals("Individual")){
            Individual individual = userHibControl.getIndividualById(userId);
            accLoginF.setText(user.getLogin());
            accEmailF.setText(user.getEmail());
            accAddressF.setText(user.getAddress());
            accNameF.setText(individual.getName());
            accSurnameF.setText(individual.getSurname());
            accPhoneNumF.setText(individual.getPhoneNumber());
            accBirthdateF.setValue(individual.getBirthDate());
        }
        else if(user.getClass().getSimpleName().equals("LegalPerson")){
            LegalPerson legalPerson = userHibControl.getLegalPersonById(userId);
            accLoginF.setText(user.getLogin());
            accEmailF.setText(user.getEmail());
            accAddressF.setText(user.getAddress());
            accCompTitleF.setText(legalPerson.getCompanyTitle());
            accCeoF.setText(legalPerson.getCeo());
            accRegNumF.setText(legalPerson.getRegistrationNumber());
            accVatNumF.setText(legalPerson.getVatNumber());
        }
    }
    public void updateAccInfo(ActionEvent actionEvent) {
        User user = userHibControl.getUserById(userId);
        if(user.getClass().getSimpleName().equals("Individual")){
            Individual individual = userHibControl.getIndividualById(userId);
            user.setLogin(accLoginF.getText());
            user.setEmail(accEmailF.getText());
            user.setAddress(accAddressF.getText());
            individual.setName(accNameF.getText());
            individual.setSurname(accSurnameF.getText());
            individual.setPhoneNumber(accPhoneNumF.getText());
            individual.setBirthDate(accBirthdateF.getValue());
            userHibControl.updateUser(user);
            userHibControl.updateUser(individual);
        }
        else if(user.getClass().getSimpleName().equals("LegalPerson")){
            LegalPerson legalPerson = userHibControl.getLegalPersonById(userId);
            user.setLogin(accLoginF.getText());
            user.setEmail(accEmailF.getText());
            user.setAddress(accAddressF.getText());
            legalPerson.setCompanyTitle(accCompTitleF.getText());
            legalPerson.setCeo(accCeoF.getText());
            legalPerson.setRegistrationNumber(accRegNumF.getText());
            legalPerson.setVatNumber(accVatNumF.getText());
            userHibControl.updateUser(user);
            userHibControl.updateUser(legalPerson);
        }
    }

    public void changeAccPassword(ActionEvent actionEvent) {
        User user = userHibControl.getUserById(userId);

        if(accCurrentPassF.getText().equals(user.getPassword())) {
            if(accNewPassF.getText().equals(accConfirmPassF.getText()) && (!accNewPassF.getText().isEmpty() || !accConfirmPassF.getText().isEmpty())){
                user.setPassword(accNewPassF.getText());
                userHibControl.updateUser(user);
            } else {
                alertMessage("New password and confirm password doesn't match!\n Or new password one of fields is empty!");
            }
        } else {
            alertMessage("Wrong current password!");
        }
    }
    //------------------------------------------MY ACCOUNT TAB END-------------------------------------------//

    private Book getBookById(String id) {
        return bookHibControl.getBookById(Integer.parseInt(id));
    }

    private void refreshTable() {
        shopBookList.getItems().clear();
        ArrayList<Book> books = DatabaseOperations.getAllBooksFromDb();
        books.forEach(b -> shopBookList.getItems().add(b.getBookTitle()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        refreshTable();
        genre.getItems().clear();
        searchGenre.getItems().clear();
        filterByStatusF.getItems().clear();
        genre.getItems().addAll("SCI_FI", "FANTASY", "MYSTERY", "ROMANCE", "DRAMA", "ACTION", "THRILLER", "MANGA");
        searchGenre.getItems().addAll("SCI_FI", "FANTASY", "MYSTERY", "ROMANCE", "DRAMA", "ACTION", "THRILLER", "MANGA");
        filterByStatusF.getItems().addAll("VERIFIED", "REJECTED", "ON_HOLD", "PROCESSING");
        //fillTables();
    }

}

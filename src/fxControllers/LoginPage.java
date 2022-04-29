package fxControllers;

import bookds.User;
import hibernateControllers.BookHibControl;
import hibernateControllers.CartHibControl;
import hibernateControllers.UserHibControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.DatabaseOperations;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;

import static utils.WarningMessages.alertMessage;

public class LoginPage {
    @FXML
    public TextField loginF;
    @FXML
    public PasswordField passwordF;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);


    public void validateAndLoadLogin(ActionEvent actionEvent) throws IOException, SQLException {
        User user = userHibControl.getUserByLoginData(loginF.getText(), passwordF.getText());

        if(user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-book-shop-page.fxml"));
            Parent root = fxmlLoader.load();

            MainBookShopPage mainBookShopPage = fxmlLoader.getController();
            mainBookShopPage.setUserData(user.getId());

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginF.getScene().getWindow();
            stage.setTitle("Book store");
            stage.setScene(scene);
            stage.show();
        } else {
            alertMessage("No such user");
        }


        /*if(DatabaseOperations.userExists(loginF.getText(),passwordF.getText())) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-book-shop-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            //Stage stage = new Stage(); kurs daug langu
            Stage stage = (Stage) loginF.getScene().getWindow();
            stage.setTitle("Book store");
            stage.setScene(scene);
            stage.show();
        } else {
            alertMessage("No such user");
        }*/

    }

    public void loadSignUpForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/sign-up-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Stage stage = new Stage(); kurs daug langu
        Stage stage = (Stage) loginF.getScene().getWindow();
        stage.setTitle("Book store");
        stage.setScene(scene);
        stage.show();
    }
}

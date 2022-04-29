package fxControllers;

import bookds.Individual;
import bookds.LegalPerson;
import bookds.Role;
import hibernateControllers.UserHibControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DatabaseOperations;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static utils.WarningMessages.alertMessage;

public class SignUpPage implements Initializable {
    @FXML
    public TextField loginF;
    @FXML
    public PasswordField passwordF;
    @FXML
    public RadioButton individualRadio;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton legalRadio;
    @FXML
    public TextField individualNameF;
    @FXML
    public TextField individualSurnameF;
    @FXML
    public TextField emailF;
    @FXML
    public TextField AddressF;
    @FXML
    public TextField phoneNumF;
    @FXML
    public TextField companyTitleF;
    @FXML
    public DatePicker birthdateF;
    @FXML
    public TextField ceoF;
    @FXML
    public TextField regNumF;
    @FXML
    public TextField vatNumF;
    @FXML
    public ComboBox roleCombo;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);

    public void registerAndReturn(ActionEvent actionEvent) throws SQLException, IOException {

        if(DatabaseOperations.userInfoAlreadyExists(loginF.getText(), passwordF.getText(), emailF.getText(), phoneNumF.getText())) {
            alertMessage("User with these credentials already exist.\nPlease choose another credentials.");
        } else {
            if (individualRadio.isSelected()) {
                if(loginF.getText().isEmpty() || passwordF.getText().isEmpty() || emailF.getText().isEmpty() || AddressF.getText().isEmpty() ||
                roleCombo.getSelectionModel().getSelectedItem().toString().isEmpty() || individualNameF.getText().isEmpty() ||
                individualSurnameF.getText().isEmpty() || phoneNumF.getText().isEmpty() || birthdateF.getValue().toString().isEmpty()) {
                    alertMessage("All fields must be filled and cannot be left empty!");
                } else if(passwordF.getText().length() < 6) {
                    alertMessage("Password must be at least 6 symbols long!");
                } else if(!Pattern.matches("[a-zA-Z]", loginF.getText()) || !Pattern.matches("[a-zA-Z]", individualNameF.getText()) ||
                        !Pattern.matches("[a-zA-Z]", individualSurnameF.getText())) {
                    alertMessage("Login, Name or Surname fields cannot have special symbols or digits!");
                }
            else if(!emailF.getText().contains("@") || !emailF.getText().contains(".")) {
                    alertMessage("Wrong email!");
                }
                else {
                    Individual individual = new Individual(loginF.getText(),
                            passwordF.getText(),
                            emailF.getText(),
                            AddressF.getText(),
                            Role.valueOf(roleCombo.getSelectionModel().getSelectedItem().toString()),
                            individualNameF.getText(),
                            individualSurnameF.getText(),
                            phoneNumF.getText(),
                            LocalDate.parse(birthdateF.getValue().toString()));

                    userHibControl.createUser(individual);

                    returnToLogin();
                }

            } else {
                if(loginF.getText().isEmpty() || passwordF.getText().isEmpty() || emailF.getText().isEmpty() || AddressF.getText().isEmpty() ||
                        roleCombo.getSelectionModel().getSelectedItem().toString().isEmpty() || companyTitleF.getText().isEmpty() ||
                        ceoF.getText().isEmpty() || regNumF.getText().isEmpty() || vatNumF.getText().isEmpty()) {
                    alertMessage("All fields must be filled and cannot be left empty!");
                } else if(passwordF.getText().length() < 6) {
                    alertMessage("Password must be at least 6 symbols long!");
                } else if(!Pattern.matches("[a-zA-Z]", loginF.getText()) || !Pattern.matches("[a-zA-Z]", companyTitleF.getText()) ||
                        !Pattern.matches("[a-zA-Z]", ceoF.getText())) {
                    alertMessage("Login, Company tile or Company CEO fields cannot have special symbols or digits!");
                } else if(!emailF.getText().contains("@") || !emailF.getText().contains(".")) {
                    alertMessage("Wrong email!");
                }
                else {
                    LegalPerson legalPerson = new LegalPerson(loginF.getText(),
                            passwordF.getText(),
                            emailF.getText(),
                            AddressF.getText(),
                            Role.valueOf(roleCombo.getSelectionModel().getSelectedItem().toString()),
                            companyTitleF.getText(),
                            ceoF.getText(),
                            regNumF.getText(),
                            vatNumF.getText());
                    userHibControl.createUser(legalPerson);
                    returnToLogin();
                }
            }
        }
    }

    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Stage stage = new Stage(); kurs daug langu
        Stage stage = (Stage) loginF.getScene().getWindow();
        stage.setTitle("Book store");
        stage.setScene(scene);
        stage.show();
    }

    public void disableFields() {
        if(individualRadio.isSelected()) {
            companyTitleF.setDisable(true);
            ceoF.setDisable(true);
            regNumF.setDisable(true);
            vatNumF.setDisable(true);
            //individual fields
            individualNameF.setDisable(false);
            individualSurnameF.setDisable(false);
            phoneNumF.setDisable(false);
            birthdateF.setDisable(false);
        } else {
            individualNameF.setDisable(true);
            individualSurnameF.setDisable(true);
            phoneNumF.setDisable(true);
            birthdateF.setDisable(true);
            //company fields
            companyTitleF.setDisable(false);
            ceoF.setDisable(false);
            regNumF.setDisable(false);
            vatNumF.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableFields();

        roleCombo.getItems().clear();
        //roleCombo.getItems().addAll("CUSTOMER","EMPLOYEE");
        roleCombo.getItems().addAll(
                //Role.ADMIN,
                Role.CUSTOMER,
                Role.EMPLOYEE);
    }
}

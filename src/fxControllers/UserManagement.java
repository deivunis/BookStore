package fxControllers;

import bookds.*;
import hibernateControllers.UserHibControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagement implements Initializable {
    public TableColumn<UserTableParameters, Integer> colId;
    public TableColumn<UserTableParameters, String> colLogin;
    public TableColumn<UserTableParameters, String> colEmail;
    public TableColumn<UserTableParameters, String> colAddress;
    public TableColumn<UserTableParameters, String> colRole;
    public TableColumn<UserTableParameters, String> colName;
    public TableColumn<UserTableParameters, String> colSurname;
    public TableColumn<UserTableParameters, String> colPhoneNumb;
    public TableColumn<UserTableParameters, String> colCeo;
    public TableColumn<UserTableParameters, String> colCompanyTitle;
    public TableColumn<UserTableParameters, String> colRegNumber;
    public TableColumn<UserTableParameters, String> colVatNumber;
    public TableView usersTable;
    public TableColumn<UserTableParameters, Void> colAction;
    private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStore");
    UserHibControl userHibControl = new UserHibControl(entityManagerFactory);

    public UserManagement() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersTable.setEditable(true);
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colLogin.setCellFactory(TextFieldTableCell.forTableColumn());
        colLogin.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setLogin(t.getNewValue());

                    User user = userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    user.setLogin(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getLogin());
                    userHibControl.updateUser(user);
                }
        );
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(
                t -> {
                    String newName = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(newName);
                    Individual individual = (Individual) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    individual.setName(newName);
                    userHibControl.updateUser(individual);
                }
        );
        colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        colSurname.setOnEditCommit(
                t -> {
                    String newSurname = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setSurname(t.getNewValue());
                    Individual individual = (Individual) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    individual.setSurname(newSurname);
                    userHibControl.updateUser(individual);
                }
        );
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setEmail(t.getNewValue());

                    User user = userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    user.setEmail(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getEmail());
                    userHibControl.updateUser(user);
                }
        );
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        colAddress.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setAddress(t.getNewValue());

                    User user = userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    user.setAddress(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getAddress());
                    userHibControl.updateUser(user);
                }
        );
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colRole.setCellFactory(TextFieldTableCell.forTableColumn());
        colRole.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRole(String.valueOf(t.getNewValue()));

                    User user = userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    user.setRole(Role.valueOf(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getRole()));
                    userHibControl.updateUser(user);
                }
        );
        colPhoneNumb.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colPhoneNumb.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhoneNumb.setOnEditCommit(
                t -> {
                    String newPhoneNumber = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPhoneNumber(t.getNewValue());
                    Individual individual = (Individual) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    individual.setName(newPhoneNumber);
                    userHibControl.updateUser(individual);
                }
        );
        colCeo.setCellValueFactory(new PropertyValueFactory<>("ceo"));
        colCeo.setCellFactory(TextFieldTableCell.forTableColumn());
        colCeo.setOnEditCommit(
                t -> {
                    String newCeo = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setCeo(t.getNewValue());
                    LegalPerson legalPerson = (LegalPerson) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    legalPerson.setCeo(newCeo);
                    userHibControl.updateUser(legalPerson);
                }
        );
        colCompanyTitle.setCellValueFactory(new PropertyValueFactory<>("companyTitle"));
        colCompanyTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        colCompanyTitle.setOnEditCommit(
                t -> {
                    String newCompanyTitle = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setCompanyTitle(t.getNewValue());
                    LegalPerson legalPerson = (LegalPerson) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    legalPerson.setCompanyTitle(newCompanyTitle);
                    userHibControl.updateUser(legalPerson);
                }
        );
        colRegNumber.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        colRegNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        colRegNumber.setOnEditCommit(
                t -> {
                    String newRegNumber = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRegNumber(t.getNewValue());
                    LegalPerson legalPerson = (LegalPerson) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    legalPerson.setRegistrationNumber(newRegNumber);
                    userHibControl.updateUser(legalPerson);
                }
        );
        colVatNumber.setCellValueFactory(new PropertyValueFactory<>("vatNumber"));
        colVatNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        colVatNumber.setOnEditCommit(
                t -> {
                    String newVatNumber = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setVatNumber(t.getNewValue());
                    LegalPerson legalPerson = (LegalPerson) userHibControl.getUserById(t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserId());
                    legalPerson.setVatNumber(newVatNumber);
                    userHibControl.updateUser(legalPerson);
                }
        );

        Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>> cellFactory = new Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>>() {
            @Override
            public TableCell<UserTableParameters, Void> call(final TableColumn<UserTableParameters, Void> param) {
                final TableCell<UserTableParameters, Void> cell = new TableCell<UserTableParameters, Void>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            UserTableParameters data = getTableView().getItems().get(getIndex());
                            userHibControl.removeUser(data.getUserId());
                            try {
                                usersTable.getItems().clear();
                                loadUsers();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        colAction.setCellFactory(cellFactory);

        try {
            //usersTable.getItems().clear();
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() throws SQLException {
        usersTable.setEditable(true);
        List<Individual> individuals = userHibControl.getAllIndividual();
        for (Individual i : individuals) {
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setUserId(i.getId());
            userTableParameters.setLogin(i.getLogin());
            userTableParameters.setName(i.getName());
            userTableParameters.setSurname(i.getSurname());
            userTableParameters.setEmail(i.getEmail());
            userTableParameters.setAddress(i.getAddress());
            userTableParameters.setRole(i.getRole().toString());
            userTableParameters.setPhoneNumber(i.getPhoneNumber());
            data.add(userTableParameters);
        }
        List<LegalPerson> legalPersons = userHibControl.getAllLegalPersons();
        for (LegalPerson l : legalPersons) {
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setUserId(l.getId());
            userTableParameters.setLogin(l.getLogin());
            userTableParameters.setEmail(l.getEmail());
            userTableParameters.setAddress(l.getAddress());
            userTableParameters.setRole(l.getRole().toString());
            userTableParameters.setCeo(l.getCeo());
            userTableParameters.setCompanyTitle(l.getCompanyTitle());
            userTableParameters.setRegNumber(l.getRegistrationNumber());
            userTableParameters.setVatNumber(l.getVatNumber());
            data.add(userTableParameters);
        }

        usersTable.setItems(data);
    }
}

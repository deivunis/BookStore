package fxControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserTableParameters {
    private SimpleIntegerProperty userId = new SimpleIntegerProperty();
    private SimpleStringProperty login = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleStringProperty role = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private SimpleStringProperty ceo = new SimpleStringProperty();
    private SimpleStringProperty companyTitle = new SimpleStringProperty();
    private SimpleStringProperty regNumber = new SimpleStringProperty();
    private SimpleStringProperty vatNumber = new SimpleStringProperty();


    public UserTableParameters() {
    }

    public UserTableParameters(SimpleIntegerProperty userId, SimpleStringProperty login, SimpleStringProperty password, SimpleStringProperty email, SimpleStringProperty address, SimpleStringProperty role, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty phoneNumber, SimpleStringProperty ceo, SimpleStringProperty companyTitle, SimpleStringProperty regNumber, SimpleStringProperty vatNumber) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.ceo = ceo;
        this.companyTitle = companyTitle;
        this.regNumber = regNumber;
        this.vatNumber = vatNumber;
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getCeo() {
        return ceo.get();
    }

    public SimpleStringProperty ceoProperty() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo.set(ceo);
    }

    public String getCompanyTitle() {
        return companyTitle.get();
    }

    public SimpleStringProperty companyTitleProperty() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle.set(companyTitle);
    }

    public String getRegNumber() {
        return regNumber.get();
    }

    public SimpleStringProperty regNumberProperty() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber.set(regNumber);
    }

    public String getVatNumber() {
        return vatNumber.get();
    }

    public SimpleStringProperty vatNumberProperty() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber.set(vatNumber);
    }
}

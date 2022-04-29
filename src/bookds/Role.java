package bookds;

public enum Role {
    ADMIN, CUSTOMER, EMPLOYEE;

    public static Role CUSTOMER() {
        return CUSTOMER;
    }
}

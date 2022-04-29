package utils;

import bookds.Book;
import bookds.Genre;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseOperations {
    public static Connection connectToDb() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost:3306/bookshophibernate";
            String USER = "root";
            String PASS = "";
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) {
        try {
            if(connection != null) connection.close();
            if(statement != null) statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(String login, String password) throws SQLException {
        Connection connection = connectToDb();
        String selectUser = "SELECT * FROM user u WHERE u.login = ? AND u.password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            return true;
        }
        return false;
    }
    public static boolean userInfoAlreadyExists(String login, String password, String email, String phoneNumber) throws SQLException {
        Connection connection = connectToDb();
        String selectUser = "SELECT * FROM user u WHERE u.login = ? OR u.password = ? OR u.email = ? OR u.phoneNumber = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phoneNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            return true;
        }
        return false;
    }
    public static ArrayList<Book> getAllBooksFromDb() {
        ArrayList<Book> books = new ArrayList<>();
        Connection connection = DatabaseOperations.connectToDb();
        String selectQuery = "SELECT b.* FROM book AS b";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                books.add(new Book(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5),
                       resultSet.getDouble(6), resultSet.getDate(7), resultSet.getDouble(8), resultSet.getString(9),
                       resultSet.getInt(10), Genre.valueOf(resultSet.getString(11)), resultSet.getInt(12)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}

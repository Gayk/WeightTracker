package model;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private String login;
    private String email;
    private String password;

    public User(String login, String email, String password) {
        this.email = email;
        this.setPassword(password);
        this.login = login;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void delete(Connection conn) throws SQLException {
        if (id != 0) {
            String sql = "DELETE FROM users WHERE user_id= ?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            id = 0;
        }
    }

    public void saveToDB(Connection conn) throws SQLException {
        try {
            if (this.id == 0) {
                String sql = "INSERT INTO users(login, email, password) VALUES (?, ?, ?)";
                String[] generatedColumns = { "user_id" };
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.login);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE users SET login=?, email=?, password=? WHERE user_id=?";
                PreparedStatement preparedStatement;
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, this.login);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.setInt(4, this.id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Niepoprawne dane.");
        }
    }

    static public User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM users where user_id=?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("user_id");
            loadedUser.login = resultSet.getString("login");
            loadedUser.email = resultSet.getString("email");
            return loadedUser;
        }
        return null;
    }
    static public User loadUserByLogin(Connection conn, String login) throws SQLException {
        String sql = "SELECT * FROM users where login = ?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("user_id");
            loadedUser.login = resultSet.getString("login");
            loadedUser.email = resultSet.getString("email");
            return loadedUser;
        }
        return null;
    }

    static public User[] loadAllUsers(Connection conn) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("user_id");
            loadedUser.login = resultSet.getString("username");
            loadedUser.email = resultSet.getString("email");
            users.add(loadedUser);
        }
        return checkIfExist(users, "Brak użytkowników w bazie danych");
    }

    private static User[] checkIfExist(ArrayList<User> users, String message) {
        User[] uArray = new User[users.size()];
        if (users.size() == 0) {
            System.out.println(message);
            return null;
        }
        uArray = users.toArray(uArray);
        return uArray;
    }

    @Override
    public String toString() {
        return "Dane użytkownika" +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

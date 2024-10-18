package database.util;

import database.DAO.UsersDAO;
import database.connection.DatabaseConnection;
import database.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UsersDAOImpl implements UsersDAO {

    private final DatabaseConnection databaseConnection;
    private static UsersDAOImpl instance;
    private static final Lock instanceLock = new ReentrantLock();
    private static final Lock connectionLock = new ReentrantLock();

    private UsersDAOImpl() {
        databaseConnection = new DatabaseConnection();
    }

    public static UsersDAOImpl getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new UsersDAOImpl();
                }
            }
        }
        return instance;
    }

    private Connection getConnection() {
        synchronized (connectionLock) {
            return databaseConnection.getConnection();
        }
    }

    @Override
    public User create(String username, String password, String profileName) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO bruger (username, password, profilename) VALUES (?, ?, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, profileName);
            statement.executeUpdate();
            if(statement.getGeneratedKeys().next()) {
                return new User(statement.getGeneratedKeys().getInt(1), username, password, profileName);
            } else{
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    @Override
    public User readById(int id) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM bruger WHERE user_id = ?;");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new User(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("profilename"));
            } else {
                return null;
            }
        }
    }

    @Override
    public User readByUsername(String username) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM bruger WHERE username = ?;");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new User(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("profilename"));
            } else {
                return null;
            }
        }
    }

    @Override
    public ArrayList<User> readAllExceptSelf(User user) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM bruger WHERE user_id != ?;");
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(new User(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("profilename")));
            }
            return users;
        }
    }

    @Override
    public ArrayList<User> readByProfileName(String profileName) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM bruger WHERE profilename = ?;");
            statement.setString(1, profileName);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(new User(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("profilename")));
            }
            return users;
        }
    }

    @Override
    public User readByUsernameAndPassword(String username, String password) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM bruger WHERE username = ? AND password = ?;");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new User(resultSet.getInt("user_id"), resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("profilename"));
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE bruger SET username = ?, password = ?, profilename = ? WHERE user_id = ?;");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getProfileName());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(User user) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM bruger WHERE user_id = ?;");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        }
    }
}

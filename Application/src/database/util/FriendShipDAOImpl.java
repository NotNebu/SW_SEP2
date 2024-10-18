package database.util;

import database.DAO.FriendShipDAO;
import database.connection.DatabaseConnection;
import database.object.FriendShip;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FriendShipDAOImpl implements FriendShipDAO {

    private final DatabaseConnection databaseConnection;
    private static FriendShipDAOImpl instance;
    private static final Lock instanceLock = new ReentrantLock();
    private static final Lock connectionLock = new ReentrantLock();

    private FriendShipDAOImpl() {
        databaseConnection = new DatabaseConnection();
    }

    public static FriendShipDAOImpl getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new FriendShipDAOImpl();
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
    public FriendShip create(int user_id, int friend_id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.executeUpdate();
            if (statement.getGeneratedKeys().next()) {
                return new FriendShip(statement.getGeneratedKeys().getInt(1), user_id, friend_id);
            } else {
                throw new SQLException("Creating friendship failed, no ID obtained.");
            }
        }
    }

    @Override
    public ArrayList<FriendShip> readByUserID(int user_id) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendship WHERE user_id = ?;");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<FriendShip> friendShips = new ArrayList<>();
            while (resultSet.next()) {
                friendShips.add(new FriendShip(resultSet.getInt("friendship_id"),
                        resultSet.getInt("user_id"), resultSet.getInt("friend_id")));
            }
            return friendShips;
        }
    }

    @Override
    public FriendShip readByFriendIDAndUserID(int user_id, int friend_id) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendship WHERE user_id = ? AND friend_id = ?;");
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new FriendShip(resultSet.getInt("friendship_id"),
                        resultSet.getInt("user_id"), resultSet.getInt("friend_id"));
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(FriendShip friendShip) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE friendship SET user_id = ?, friend_id = ? WHERE friendship_id = ?;");
            statement.setInt(1, friendShip.getUser_id());
            statement.setInt(2, friendShip.getFriend_id());
            statement.setInt(3, friendShip.getFriendship_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(FriendShip friendShip) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM friendship WHERE friendship_id = ?;");
            statement.setInt(1, friendShip.getFriendship_id());
            statement.executeUpdate();
        }
    }
}

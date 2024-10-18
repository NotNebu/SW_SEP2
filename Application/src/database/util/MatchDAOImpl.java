package database.util;

import database.DAO.MatchDAO;
import database.connection.DatabaseConnection;
import database.object.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatchDAOImpl implements MatchDAO {

    private final DatabaseConnection databaseConnection;
    private static MatchDAOImpl instance;
    private static final Lock instanceLock = new ReentrantLock();
    private static final Lock connectionLock = new ReentrantLock();

    public MatchDAOImpl() {
        databaseConnection = new DatabaseConnection();
    }

    public static MatchDAOImpl getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new MatchDAOImpl();
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
    public Match create() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO match DEFAULT VALUES;",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            if (statement.getGeneratedKeys().next()) {
                return new Match(statement.getGeneratedKeys().getInt(1),
                        statement.getGeneratedKeys().getString(2));
            } else {
                throw new SQLException("Creating match failed, no ID obtained.");
            }
        }
    }

    @Override
    public Match readById(int match_id) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM match WHERE match_id = ?;");
            statement.setInt(1, match_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Match(resultSet.getInt("match_id"),
                        resultSet.getString("time_stamp"));
            } else {
                return null;
            }
        }
    }

    @Override
    public ArrayList<Match> readByUserId(int user_id) throws SQLException {
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM match JOIN participant ON match.match_id = participant.match_id WHERE user_id = ?;");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Match> matches = new ArrayList<>();
            while (resultSet.next()) {
                matches.add(new Match(resultSet.getInt("match_id"),
                        resultSet.getString("time_stamp")));
            }
            return matches;
        }
    }

    @Override
    public void update(Match match) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE match SET time_stamp = ? WHERE match_id = ?;");
            statement.setString(1, match.getTime_stamp());
            statement.setInt(2, match.getMatch_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Match match) throws SQLException {
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM match WHERE match_id = ?;");
            statement.setInt(1, match.getMatch_id());
            statement.executeUpdate();
        }
    }
}

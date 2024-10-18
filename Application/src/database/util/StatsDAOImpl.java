package database.util;

import database.DAO.StatsDAO;
import database.connection.DatabaseConnection;
import database.object.Stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StatsDAOImpl implements StatsDAO {

    private final DatabaseConnection databaseConnection;
    private static StatsDAOImpl instance;
    private static final Lock instanceLock = new ReentrantLock();
    private static final Lock connectionLock = new ReentrantLock();

    private StatsDAOImpl() {
        databaseConnection = new DatabaseConnection();
    }

    public static StatsDAOImpl getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new StatsDAOImpl();
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
    public Stats create(int user_id, float elo, float avg_WPM, float avg_Accuracy, float winRate, int total_matches) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO stats (user_id, elo, avg_WPM, avg_Accuracy, winRate, total_matches) VALUES (?, ?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, user_id);
            statement.setFloat(2, elo);
            statement.setFloat(3, avg_WPM);
            statement.setFloat(4, avg_Accuracy);
            statement.setFloat(5, winRate);
            statement.setInt(6, total_matches);
            statement.executeUpdate();
            if (statement.getGeneratedKeys().next()) {
                return new Stats(statement.getGeneratedKeys().getInt(1), user_id, elo, avg_WPM, avg_Accuracy, winRate, total_matches);
            } else {
                throw new SQLException("Creating stats failed, no ID obtained.");
            }
        }
    }

    @Override
    public Stats readByID(int stats_id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM stats WHERE stats_id = ?;");
            statement.setInt(1, stats_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Stats(resultSet.getInt("stats_id"), resultSet.getInt("user_id"),
                        resultSet.getFloat("elo"), resultSet.getFloat("avg_WPM"),
                        resultSet.getFloat("avg_Accuracy"), resultSet.getFloat("winRate"),
                        resultSet.getInt("total_matches"));
            } else {
                return null;
            }
        }
    }

    @Override
    public Stats readByUserID(int user_id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM stats WHERE user_id = ?;");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Stats(resultSet.getInt("stats_id"), resultSet.getInt("user_id"),
                        resultSet.getFloat("elo"), resultSet.getFloat("avg_WPM"),
                        resultSet.getFloat("avg_Accuracy"), resultSet.getFloat("winRate"),
                        resultSet.getInt("total_matches"));
            } else {
                return null;
            }
        }
    }

    @Override
    public void update(Stats stats) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE stats SET elo = ?, avg_WPM = ?, avg_Accuracy = ?, winRate = ?, total_matches = ? WHERE stats_id = ?;");
            statement.setFloat(1, stats.getElo());
            statement.setFloat(2, stats.getAvg_WPM());
            statement.setFloat(3, stats.getAvg_Accuracy());
            statement.setFloat(4, stats.getWinRate());
            statement.setInt(5, stats.getTotal_matches());
            statement.setInt(6, stats.getStats_id());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Stats stats) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM stats WHERE stats_id = ?;");
            statement.setInt(1, stats.getStats_id());
            statement.executeUpdate();
        }
    }
}

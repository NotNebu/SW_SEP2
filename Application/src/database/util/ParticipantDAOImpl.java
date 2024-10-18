package database.util;

import database.DAO.ParticipantDAO;
import database.connection.DatabaseConnection;
import database.object.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParticipantDAOImpl implements ParticipantDAO {

    private final DatabaseConnection databaseConnection;
    private static ParticipantDAOImpl instance;
    private static final Lock instanceLock = new ReentrantLock();
    private static final Lock connectionLock = new ReentrantLock();

    private ParticipantDAOImpl() {
        databaseConnection = new DatabaseConnection();
    }

    public static ParticipantDAOImpl getInstance() {
        if (instance == null) {
            synchronized (instanceLock) {
                if (instance == null) {
                    instance = new ParticipantDAOImpl();
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
    public Participant create(int user_id, int match_id, String match_result, float elo_change, float avg_WPM, float accuracy) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO participant (user_id, match_id, match_result, elo_change, avg_wpm, accuracy) VALUES (?, ?, ?, ?, ?, ?);");
            statement.setInt(1, user_id);
            statement.setInt(2, match_id);
            statement.setString(3, match_result);
            statement.setFloat(4, elo_change);
            statement.setFloat(5, avg_WPM);
            statement.setFloat(6, accuracy);
            statement.executeUpdate();
            return new Participant(user_id, match_id, match_result, elo_change, avg_WPM, accuracy);
        }
    }

    @Override
    public Participant readById(int user_id, int match_id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM participant WHERE user_id = ? AND match_id = ?;");
            statement.setInt(1, user_id);
            statement.setInt(2, match_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Participant(resultSet.getInt("user_id"), resultSet.getInt("match_id"),
                        resultSet.getString("match_result"), resultSet.getFloat("elo_change"),
                        resultSet.getFloat("avg_wpm"), resultSet.getFloat("accuracy"));
            } else {
                return null;
            }
        }
    }

    @Override
    public ArrayList<Participant> readByMatchId(int match_id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM participant WHERE match_id = ?;");
            statement.setInt(1, match_id);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                participants.add(new Participant(resultSet.getInt("user_id"), resultSet.getInt("match_id"),
                        resultSet.getString("match_result"), resultSet.getFloat("elo_change"),
                        resultSet.getFloat("avg_WPM"), resultSet.getFloat("accuracy")));
            }
            return participants;
        }
    }

    @Override
    public ArrayList<Participant> readByUserId(int user_id) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM participant WHERE user_id = ?;");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                participants.add(new Participant(resultSet.getInt("user_id"), resultSet.getInt("match_id"),
                        resultSet.getString("match_result"), resultSet.getFloat("elo_change"),
                        resultSet.getFloat("avg_WPM"), resultSet.getFloat("accuracy")));
            }
            return participants;
        }
    }

    @Override
    public void update(Participant participant) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE participant SET match_result = ?, elo_change = ?, avg_WPM = ?, accuracy = ? WHERE user_id = ? AND match_id = ?;");
            statement.setString(1, participant.getMatchResult());
            statement.setFloat(2, participant.getEloChange());
            statement.setFloat(3, participant.getAvgWPM());
            statement.setFloat(4, participant.getAccuracy());
            statement.setInt(5, participant.getUserId());
            statement.setInt(6, participant.getMatchId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Participant participant) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM participant WHERE user_id = ? AND match_id = ?;");
            statement.setInt(1, participant.getUserId());
            statement.setInt(2, participant.getMatchId());
            statement.executeUpdate();
        }
    }

}
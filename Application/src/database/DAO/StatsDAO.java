package database.DAO;

import database.object.Stats;

import java.sql.SQLException;

public interface StatsDAO {

    Stats create(int user_id, float elo, float avg_WPM, float avg_Accuracy, float winRate, int total_matches) throws SQLException;

    Stats readByID(int stats_id) throws SQLException;

    Stats readByUserID(int user_id) throws SQLException;

    void update(Stats stats) throws SQLException;

    void delete(Stats stats) throws SQLException;


}

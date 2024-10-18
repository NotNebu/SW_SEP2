package database.proxies;

import database.DAO.StatsDAO;
import database.object.Stats;
import database.util.StatsDAOImpl;

import java.sql.SQLException;

public class StatsDAOProxy implements StatsDAO {

    private final StatsDAOImpl statsDAO;

    public StatsDAOProxy() {
        statsDAO = StatsDAOImpl.getInstance();
    }

    @Override
    public Stats create(int user_id, float elo, float avg_WPM, float avg_Accuracy, float winRate, int total_matches) throws SQLException {
        return statsDAO.create(user_id, elo, avg_WPM, avg_Accuracy, winRate, total_matches);
    }

    @Override
    public Stats readByID(int stats_id) throws SQLException {
        return statsDAO.readByID(stats_id);
    }

    @Override
    public Stats readByUserID(int user_id) throws SQLException {
        return statsDAO.readByUserID(user_id);
    }

    @Override
    public void update(Stats stats) throws SQLException {
        statsDAO.update(stats);
    }

    @Override
    public void delete(Stats stats) throws SQLException {
        statsDAO.delete(stats);
    }
}

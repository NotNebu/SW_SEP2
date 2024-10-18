package database.proxies;

import database.DAO.MatchDAO;
import database.object.Match;
import database.util.MatchDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class MatchDAOProxy implements MatchDAO {

    private final MatchDAOImpl matchDAO;

    public MatchDAOProxy() {
        matchDAO = MatchDAOImpl.getInstance();
    }

    @Override
    public Match create() throws SQLException {
        return matchDAO.create();
    }

    @Override
    public Match readById(int match_id) throws SQLException {
        return matchDAO.readById(match_id);
    }

    @Override
    public ArrayList<Match> readByUserId(int user_id) throws SQLException {
        return matchDAO.readByUserId(user_id);
    }

    @Override
    public void update(Match match) throws SQLException {
        matchDAO.update(match);
    }

    @Override
    public void delete(Match match) throws SQLException {
        matchDAO.delete(match);
    }
}

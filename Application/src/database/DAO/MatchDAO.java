package database.DAO;

import database.object.Match;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchDAO {

    Match create() throws SQLException;

    Match readById(int match_id) throws SQLException;

    ArrayList<Match> readByUserId(int user_id) throws SQLException;

    void update(Match match) throws SQLException;

    void delete(Match match) throws SQLException;

}

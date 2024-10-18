package database.DAO;

import database.object.Participant;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ParticipantDAO {

    Participant create(int user_id, int match_id, String match_result, float elo_change,
                       float avg_WPM, float accuracy) throws SQLException;

    Participant readById(int user_id, int match_id) throws SQLException;

    ArrayList<Participant> readByMatchId(int match_id) throws SQLException;

    ArrayList<Participant> readByUserId(int user_id) throws SQLException;

    void update(Participant participant) throws SQLException;

    void delete(Participant participant) throws SQLException;

}

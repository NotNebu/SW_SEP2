package database.proxies;

import database.DAO.ParticipantDAO;
import database.object.Participant;
import database.util.ParticipantDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipantDAOProxy implements ParticipantDAO {

    private final ParticipantDAOImpl participantDAO;

    public ParticipantDAOProxy() {
        participantDAO = ParticipantDAOImpl.getInstance();
    }

    @Override
    public Participant create(int user_id, int match_id, String match_result, float elo_change, float avg_WPM, float accuracy) throws SQLException {
        return participantDAO.create(user_id, match_id, match_result, elo_change, avg_WPM, accuracy);
    }

    @Override
    public Participant readById(int user_id, int match_id) throws SQLException {
        return participantDAO.readById(user_id, match_id);
    }

    @Override
    public ArrayList<Participant> readByMatchId(int match_id) throws SQLException {
        return participantDAO.readByMatchId(match_id);
    }

    @Override
    public ArrayList<Participant> readByUserId(int user_id) throws SQLException {
        return participantDAO.readByUserId(user_id);
    }

    @Override
    public void update(Participant participant) throws SQLException {
        participantDAO.update(participant);
    }

    @Override
    public void delete(Participant participant) throws SQLException {
        participantDAO.delete(participant);
    }
}

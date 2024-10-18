package database;

import database.DAO.*;
import database.proxies.*;

public class DAOManager {

    private FriendShipDAO friendShipDAO;
    private StatsDAO statsDAO;
    private UsersDAO userDAO;
    private ParticipantDAO participantDAO;
    private MatchDAO matchDAO;

    public DAOManager() {
        friendShipDAO = new FriendShipDAOProxy();
        statsDAO = new StatsDAOProxy();
        userDAO = new UsersDAOProxy();
        participantDAO = new ParticipantDAOProxy();
        matchDAO = new MatchDAOProxy();
    }

    public FriendShipDAO getFriendShipDAO() {
        return friendShipDAO;
    }

    public StatsDAO getStatsDAO() {
        return statsDAO;
    }

    public UsersDAO getUsersDAO() {
        return userDAO;
    }

    public ParticipantDAO getParticipantDAO() {
        return participantDAO;
    }

    public MatchDAO getMatchDAO() {
        return matchDAO;
    }

}

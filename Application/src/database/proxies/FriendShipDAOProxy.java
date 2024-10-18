package database.proxies;

import database.DAO.FriendShipDAO;
import database.object.FriendShip;
import database.util.FriendShipDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class FriendShipDAOProxy implements FriendShipDAO {
    private final FriendShipDAOImpl friendShipDAO;

    public FriendShipDAOProxy() {
        friendShipDAO = FriendShipDAOImpl.getInstance();
    }

    @Override
    public FriendShip create(int user_id, int friend_id) throws SQLException {
        return friendShipDAO.create(user_id, friend_id);
    }

    @Override
    public ArrayList<FriendShip> readByUserID(int user_id) throws SQLException {
        return friendShipDAO.readByUserID(user_id);
    }

    @Override
    public FriendShip readByFriendIDAndUserID(int user_id, int friend_id) throws SQLException {
        return friendShipDAO.readByFriendIDAndUserID(user_id, friend_id);
    }

    @Override
    public void update(FriendShip friendShip) throws SQLException {
        friendShipDAO.update(friendShip);
    }

    @Override
    public void delete(FriendShip friendShip) throws SQLException {
        friendShipDAO.delete(friendShip);
    }
}

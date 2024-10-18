package database.DAO;

import database.object.FriendShip;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FriendShipDAO {

    FriendShip create(int user_id, int friend_id) throws SQLException;
    ArrayList<FriendShip> readByUserID(int user_id) throws SQLException;

    FriendShip readByFriendIDAndUserID(int user_id, int friend_id) throws SQLException;

    void update(FriendShip friendShip) throws SQLException;

    void delete(FriendShip friendShip) throws SQLException;

}

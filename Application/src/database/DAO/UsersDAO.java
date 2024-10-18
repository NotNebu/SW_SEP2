package database.DAO;

import database.object.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UsersDAO {

    User create(String username, String password, String profileName) throws SQLException;
    User readById(int id) throws SQLException;
    User readByUsername(String username) throws SQLException;
    ArrayList<User> readAllExceptSelf(User user) throws SQLException;
    ArrayList<User> readByProfileName(String profileName) throws SQLException;
    User readByUsernameAndPassword(String username, String password) throws SQLException;
    void update(User user) throws SQLException;
    void delete(User user) throws SQLException;

}

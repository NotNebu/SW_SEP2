package database.proxies;

import database.DAO.UsersDAO;
import database.object.User;
import database.util.UsersDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAOProxy implements UsersDAO {
    private final UsersDAOImpl usersDAO;
    private ArrayList<User> users;

    public UsersDAOProxy() {
        this.usersDAO = UsersDAOImpl.getInstance();
        users = new ArrayList<>();
    }

    @Override
    public User create(String username, String password, String profileName) throws SQLException {
        return usersDAO.create(username, password, profileName);
    }

    @Override
    public User readById(int id) throws SQLException {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return usersDAO.readById(id);
    }

    @Override
    public User readByUsername(String username) throws SQLException {
        return usersDAO.readByUsername(username);
    }

    @Override
    public ArrayList<User> readAllExceptSelf(User user) throws SQLException {
        return usersDAO.readAllExceptSelf(user);
    }

    @Override
    public ArrayList<User> readByProfileName(String profileName) throws SQLException {
        return usersDAO.readByProfileName(profileName);
    }

    @Override
    public User readByUsernameAndPassword(String username, String password) throws SQLException {
        for(User user : users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return usersDAO.readByUsernameAndPassword(username, password);
    }

    @Override
    public void update(User user) throws SQLException {
        usersDAO.update(user);
        users.clear();
    }

    @Override
    public void delete(User user) throws SQLException {
        usersDAO.delete(user);
    }
}

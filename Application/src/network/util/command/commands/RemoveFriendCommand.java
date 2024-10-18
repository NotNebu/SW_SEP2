package network.util.command.commands;

import database.DAO.FriendShipDAO;
import database.DAO.UsersDAO;
import database.object.FriendShip;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.RemoveFriendRequestMessage;
import network.util.message.response.FriendsUpdatedResponseMessage;

import java.sql.SQLException;

public class RemoveFriendCommand extends BaseCommand {

    private RemoveFriendRequestMessage message;
    private FriendShipDAO friendShipDAO;
    private UsersDAO usersDAO;

    public RemoveFriendCommand(SocketServer.ClientHandler clientHandler, RemoveFriendRequestMessage message) {
        super(clientHandler);
        this.message = message;
        this.friendShipDAO = clientHandler.getDataManager().getFriendShipDAO();
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
    }

    @Override
    public void execute() {
        User friend;
        try {
            friend = usersDAO.readByUsername(message.getFriendName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FriendShip friendShip;
        try {
            friendShip = friendShipDAO.readByFriendIDAndUserID(Integer.parseInt(message.getId()), friend.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            friendShipDAO.delete(friendShip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clientHandler.sendMessage(new FriendsUpdatedResponseMessage());
    }
}

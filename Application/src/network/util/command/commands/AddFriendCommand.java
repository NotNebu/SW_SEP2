package network.util.command.commands;

import database.DAO.FriendShipDAO;
import database.DAO.UsersDAO;
import database.object.FriendShip;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.AddFriendRequestMessage;
import network.util.message.response.AddFriendResponseMessage;
import network.util.message.response.FriendsUpdatedResponseMessage;

import java.sql.SQLException;

public class AddFriendCommand extends BaseCommand {

    private AddFriendRequestMessage message;
    private UsersDAO usersDAO;
    private FriendShipDAO friendShipDAO;

    public AddFriendCommand(SocketServer.ClientHandler clientHandler, AddFriendRequestMessage message) {
        super(clientHandler);
        this.message = message;
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
        this.friendShipDAO = clientHandler.getDataManager().getFriendShipDAO();
    }

    @Override
    public void execute() {
        User user;
        try {
            user = usersDAO.readById(Integer.parseInt(message.getUserID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        User friendUser = null;
        try {
            friendUser = usersDAO.readByUsername(message.getFriendUsername());
        } catch (SQLException e) {
            clientHandler.sendMessage(new AddFriendResponseMessage(false));
        }
        if (friendUser == null && user == null) {
            clientHandler.sendMessage(new AddFriendResponseMessage(false));
        } else {
            FriendShip friendShip = null;
            try {
                assert friendUser != null;
                friendShip = friendShipDAO.readByFriendIDAndUserID(user.getId(), friendUser.getId());
            } catch (SQLException e) {
                clientHandler.sendMessage(new AddFriendResponseMessage(false));
            }
            if (friendShip != null) {
                clientHandler.sendMessage(new AddFriendResponseMessage(false));
            } else {
                try {
                    friendShipDAO.create(user.getId(), friendUser.getId());
                    clientHandler.sendMessage(new AddFriendResponseMessage(true));
                    clientHandler.sendMessage(new FriendsUpdatedResponseMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

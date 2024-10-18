package network.util.command.commands;

import database.DAO.UsersDAO;
import database.object.User;
import model.user.UserDTO;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.GetUsernamesForFriendRequestMessage;
import network.util.message.response.GetUsernamesForAddFriendResponseMessage;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetUsernamesForFriendsCommand extends BaseCommand {

    private GetUsernamesForFriendRequestMessage message;
    private UsersDAO usersDAO;

    public GetUsernamesForFriendsCommand(SocketServer.ClientHandler clientHandler, GetUsernamesForFriendRequestMessage message) {
        super(clientHandler);
        this.message = message;
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
    }

    @Override
    public void execute() {
        ArrayList<User> friends;

        try {
            friends = usersDAO.readAllExceptSelf(new User(Integer.parseInt(message.getId()), message.getUsername(), null, null));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> usernames = new ArrayList<>();
        for(User friend : friends){
            usernames.add(friend.getUsername());
        }
        clientHandler.sendMessage(new GetUsernamesForAddFriendResponseMessage(usernames));
    }
}

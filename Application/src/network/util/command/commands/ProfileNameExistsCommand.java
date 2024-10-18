package network.util.command.commands;

import database.DAO.UsersDAO;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.ProfileNameExistsRequestMessage;
import network.util.message.response.ProfileNameExistsResponseMessage;
import network.util.message.response.UserNameExistResponseMessage;

import java.util.ArrayList;

public class ProfileNameExistsCommand extends BaseCommand {

    private ProfileNameExistsRequestMessage message;
    private UsersDAO usersDAO;


    public ProfileNameExistsCommand(SocketServer.ClientHandler clientHandler, ProfileNameExistsRequestMessage message) {
        super(clientHandler);
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
        this.message = message;
    }

    @Override
    public void execute() {
        ArrayList<User> users = null;
        try {
            users = usersDAO.readByProfileName(message.getProfileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (users.isEmpty()) {
            clientHandler.sendMessage(new ProfileNameExistsResponseMessage(false));
        } else {
            clientHandler.sendMessage(new ProfileNameExistsResponseMessage(true));
        }
    }
}

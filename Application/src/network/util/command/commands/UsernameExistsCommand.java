package network.util.command.commands;

import database.DAO.UsersDAO;
import database.DAOManager;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.UserNameExistRequestMessage;
import network.util.message.response.UserNameExistResponseMessage;

import java.util.ArrayList;

public class UsernameExistsCommand extends BaseCommand {

    private UserNameExistRequestMessage message;
    private UsersDAO usersDAO;

    public UsernameExistsCommand(SocketServer.ClientHandler clientHandler, UserNameExistRequestMessage message) {
        super(clientHandler);
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
        this.message = message;
    }

    @Override
    public void execute() {
        User user = null;
        try {
            user = usersDAO.readByUsername(message.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            clientHandler.sendMessage(new UserNameExistResponseMessage(false));
        } else {
            clientHandler.sendMessage(new UserNameExistResponseMessage(true));
        }
    }
}

package network.util.command.commands;

import database.DAO.UsersDAO;
import database.object.User;
import network.server.socket.SocketServer;
import network.util.command.BaseCommand;
import network.util.message.request.LoginRequestMessage;
import network.util.message.response.LoginResponseMessage;

import java.sql.SQLException;

public class LoginCommand extends BaseCommand {

    private LoginRequestMessage message;

    private UsersDAO usersDAO;

    public LoginCommand(SocketServer.ClientHandler clientHandler, LoginRequestMessage message) {
        super(clientHandler);
        this.message = message;
        this.usersDAO = clientHandler.getDataManager().getUsersDAO();
    }

    @Override
    public void execute() {
        User user;
        try {
            user = usersDAO.readByUsernameAndPassword(message.getUsername(), message.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(user == null) {
            clientHandler.sendMessage(new LoginResponseMessage(false, null, null));
        }else{
            clientHandler.sendMessage(new LoginResponseMessage(true, user.getUsername(), String.valueOf(user.getId())));
        }
    }
}
